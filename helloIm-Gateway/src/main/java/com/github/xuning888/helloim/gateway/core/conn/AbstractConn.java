package com.github.xuning888.helloim.gateway.core.conn;


import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import com.github.xuning888.helloim.gateway.utils.TimeWheelUtils;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuning
 * @date 2025/12/6 22:42
 */
public abstract class AbstractConn implements Conn {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConn.class);

    // 下行消息最大重试次数
    private static final int MAX_RETRY_TIMES = 3;

    // 重试间隔时间
    private static final int delayMills = 100;

    private final Channel channel;

    private final MsgPipeline msgPipeline;

    private final String id;

    private final AtomicInteger atomicInteger;

    private final ConcurrentMap<String, InFlightMessage> inFlightMessages = new ConcurrentHashMap<>();

    public AbstractConn(Channel channel, MsgPipeline msgPipeline) {
        this.channel = channel;
        this.msgPipeline = msgPipeline;
        this.atomicInteger = new AtomicInteger(0);
        this.id = String.valueOf(channel.getId());
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Channel channel() {
        return this.channel;
    }

    @Override
    public MsgPipeline getMsgPipeline() {
        return this.msgPipeline;
    }

    @Override
    public synchronized void writeMessage(Frame frame, boolean needAck, String traceId) {
        if (needAck) {
            int seq = atomicInteger.getAndIncrement(); // 重新分配客户端
            frame.getHeader().setSeq(seq); // 下行指令 + 下行cmdId拼接key
            this.write(frame, traceId);
            appendMessage(frame, traceId);
        } else {
            this.write(frame, traceId);
        }
    }

    @Override
    public boolean ack(Frame frame) {
        String key = frame.key();
        boolean ack = removeInFlight(key);
        if (ack) {
            return true;
        }
        int req = frame.getHeader().getReq();
        return Header.RES == req;
    }

    @Override
    public void clearInFlightMessage() {
        Set<String> keys = inFlightMessages.keySet();
        for (String key : keys) {
            removeInFlight(key);
        }
    }

    /**
     * 下行消息发送给到peer
     */
    public abstract void write(Frame frame, String traceId);

    private void appendMessage(Frame frame, String traceId) {
        String key = frame.key();
        if (inFlightMessages.containsKey(key)) {
            logger.warn("Duplicate message, key: {}, connId: {}, traceId: {}", key, id, traceId);
            return;
        }
        int retryTimes = 1;
        MessageTask messageTask = new MessageTask(frame, traceId, retryTimes);
        Timeout timeout = TimeWheelUtils.addTask(messageTask, delayMills, TimeUnit.MILLISECONDS);
        InFlightMessage inFlightMessage = new InFlightMessage(frame, traceId, retryTimes, timeout);
        inFlightMessages.put(key, inFlightMessage);
    }

    // 把消息重新放到飞行队列
    private void retryAppendMessage(Frame frame, String traceId, int retryTimes) {
        // 达到最大的重试次数, 不再重试
        if (retryTimes >= MAX_RETRY_TIMES) {
            String key = frame.key();
            removeInFlight(key);
            return;
        }
        retryTimes++;
        MessageTask messageTask = new MessageTask(frame, traceId, retryTimes);
        Timeout timeout = TimeWheelUtils.addTask(messageTask, delayMills * 10, TimeUnit.MILLISECONDS);
        String key = frame.key();
        InFlightMessage inFlightMessage = new InFlightMessage(frame, traceId, retryTimes, timeout);
        inFlightMessages.put(key, inFlightMessage);
    }

    private boolean removeInFlight(String key) {
        InFlightMessage inFlightMessage = inFlightMessages.remove(key);
        if (inFlightMessage != null) {
            inFlightMessage.timeout.cancel();
            return true;
        }
        return false;
    }


    // 下行飞行消息
    private static class InFlightMessage {
        final Frame frame;
        final String traceId;
        final int retryTimes;
        final Timeout timeout;
        public InFlightMessage(Frame frame, String traceId, int retryTimes, Timeout timeout) {
            this.frame = frame;
            this.traceId = traceId;
            this.retryTimes = retryTimes;
            this.timeout = timeout;
        }
    }

    private class MessageTask implements TimerTask {

        final Frame frame;
        final String traceId;
        final int retryTimes;

        public MessageTask(Frame frame, String traceId, int retryTimes) {
            this.frame =frame;
            this.traceId = traceId;
            this.retryTimes = retryTimes;
        }

        @Override
        public void run(Timeout timeout) throws Exception {
            String key = frame.key();
            int retryTimes = this.retryTimes;
            if (!inFlightMessages.containsKey(key)) {
                logger.debug("MessageTask, message already ack, key: {}, traceId: {}", key, traceId);
                return;
            }

            // 连接是否可用?
            if (!AbstractConn.this.isOk()) {
                logger.warn("MessageTask, channel is closed, cannot retry message, key: {}, traceId: {}", key, traceId);
                AbstractConn.this.removeInFlight(key);
                return;
            }

            try {
                // 重发消息
                AbstractConn.this.write(frame, traceId);
                logger.warn("MessageTask, retried, key: {}, retryTimes: {}, traceId: {}", key, retryTimes, traceId);
                // 重新添加到充实队列
                AbstractConn.this.retryAppendMessage(frame, traceId, retryTimes);
            } catch (Exception ex) {
                logger.error("MessageTask, Failed to retry write message key: {}, traceId: {}", key, traceId, ex);
                AbstractConn.this.removeInFlight(key);
            }
        }
    }
}
