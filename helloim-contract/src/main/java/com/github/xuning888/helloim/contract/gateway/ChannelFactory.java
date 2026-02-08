package com.github.xuning888.helloim.contract.gateway;


import com.github.xuning888.helloim.api.protobuf.common.v1.Endpoint;
import io.grpc.ConnectivityState;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 因为网关机有很多, 所以需要在endpoint维度复用grpc的ManagedChannel
 *
 * @author xuning
 * @date 2026/2/8 13:16
 */
public class ChannelFactory implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(ChannelFactory.class);

    private final ConcurrentHashMap<String, ManagedChannel> connPool = new ConcurrentHashMap<>();
    private final int keepaliveTime;
    private final int keepaliveTimeout;
    private final int idleTimeout;
    private final int maxRetryAttempts;
    private final ReentrantLock lock = new ReentrantLock();

    public ChannelFactory(int keepaliveTime, int keepaliveTimeout,
                          int idleTimeout, int maxRetryAttempts) {
        this.keepaliveTime = keepaliveTime;
        this.keepaliveTimeout = keepaliveTimeout;
        this.idleTimeout = idleTimeout;
        this.maxRetryAttempts = maxRetryAttempts;
    }

    /**
     * 保证endpoint ----> channel 是单例的, 性能有点差，但是无所谓了
     */
    public ManagedChannel getChannel(Endpoint endpoint) {
        String target = target(endpoint);
        ManagedChannel channel = connPool.get(target);
        if (channelHealthy(channel)) {
            return channel;
        }
        try {
            lock.lock();
            channel = connPool.get(target);
            if (channelHealthy(channel)) {
                return channel;
            }
            if (channel != null) {
                connPool.remove(target);
                safeCloseChannel(channel, target);
            }
            return connPool.computeIfAbsent(target, this::createChannel);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 检查ManagedChannel是否健康
     */
    private boolean channelHealthy(ManagedChannel channel) {
        if (channel == null || channel.isShutdown() || channel.isTerminated()) {
            return false;
        }
        try {
            ConnectivityState state = channel.getState(false);
            return state == ConnectivityState.READY || state == ConnectivityState.IDLE;
        } catch (Exception ex) {
            logger.error("channelHealthy, error: {}", ex.getMessage(), ex);
            return false;
        }
    }

    private ManagedChannel createChannel(String target) {
        return Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .keepAliveTime(keepaliveTime, TimeUnit.SECONDS)
                .keepAliveTimeout(keepaliveTimeout, TimeUnit.SECONDS)
                .idleTimeout(idleTimeout, TimeUnit.SECONDS)
                .maxRetryAttempts(maxRetryAttempts)
                .keepAliveWithoutCalls(true).build();
    }

    private String target(Endpoint endpoint) {
        return String.format("%s:%d", endpoint.getHost(), endpoint.getPort());
    }

    @Override
    public void close() throws Exception {
        for (Map.Entry<String, ManagedChannel> entry : connPool.entrySet()) {
            String target = entry.getKey();
            ManagedChannel channel = entry.getValue();
            safeCloseChannel(channel, target);
        }
        connPool.clear();
    }

    /**
     * 关闭channel，但是不会抛出异常
     */
    private void safeCloseChannel(ManagedChannel channel, String target) {
        if (channel != null && !channel.isShutdown()) {
            try {
                channel.shutdown();
                if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                    channel.shutdownNow();
                }
            } catch (InterruptedException e) {
                channel.shutdownNow();
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error("safeCloseChannel Error while closing channel for {}", target, e);
            }
        }
    }
}