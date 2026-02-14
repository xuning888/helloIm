package com.github.xuning888.helloim.gateway.core.manage;


import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.util.timer.SystemTimer;
import com.github.xuning888.helloim.gateway.config.GateServerProperties;
import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import com.github.xuning888.helloim.gateway.core.processor.Processor;
import com.github.xuning888.helloim.gateway.core.session.Session;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xuning
 * @date 2026/2/12 17:17
 */
public class RetryManager {

    private static final Logger logger = LoggerFactory.getLogger(RetryManager.class);

    private final RetryQueue[] retryQueues;
    private final long interval;
    private final int maxTimes;
    private final SystemTimer systemTimer;
    private final SessionManager sessionManager;
    private final AtomicBoolean stop;

    public RetryManager(SessionManager sessionManager, GateServerProperties.Retry retry) {
        this.stop = new AtomicBoolean(false);
        this.sessionManager = sessionManager;
        this.interval = retry.getIntervalMs();
        this.maxTimes = retry.getMaxTimes();
        this.systemTimer = new SystemTimer("RetryManager");
        retryQueues = new RetryQueue[retry.getWorkerCount()];
        for (int i = 0; i < retry.getWorkerCount(); i++) {
            RetryQueue retryQueue = new RetryQueue(this, retry.getScanIntervalMs());
            retryQueue.start();
            retryQueues[i] = retryQueue;
        }
    }

    /**
     * 添加重试消息到飞行队列
     */
    public void addRetry(RetryMessage msg) {
        if (stop.get()) {
            return;
        }
        int times = msg.getTimes();
        if (times >= maxTimes) {
            return;
        }
        String connId = msg.getConnId();
        int shard = getShard(connId);
        msg.setPri(getPri(msg.getTimes()));
        retryQueues[shard].startInFlightTimeout(connId, msg);
    }

    /**
     * 下行消息ACK,取消飞行队列中的重试消息
     */
    public void ack(String connId, int seq, int cmdId) {
        if (stop.get()) {
            return;
        }
        int shard = getShard(connId);
        retryQueues[shard].removeMessage(connId, seq, cmdId);
    }

    /**
     * 处理重试消息
     */
    public void processRetry(RetryMessage msg) {
        if (stop.get()) {
            return;
        }
        if (msg == null) {
            return;
        }
        String traceId = msg.getTraceId();
        String connId = msg.getConnId();
        // 获取用户session, 获取不到就直接return, 丢弃重试消息
        Session session = sessionManager.getSession(connId, traceId);
        if (session == null) {
            logger.warn("processRetry, getSession is null, sessionId: {}, traceId: {}", connId, traceId);
            return;
        }
        Frame frame = msg.getFrame();
        Conn conn = session.getConn();
        // 检查连接是否还可用
        if (!conn.isOk()) {
            logger.warn("processRetry, conn is not ok, uid:{}, traceId: {}", session.getUser().getUid(), traceId);
            return;
        }
        MsgPipeline msgPipeline = session.getConn().getMsgPipeline();
        Processor processor = msgPipeline.processor();
        if (processor != null) {
            // 提交到业务线程池执行, 不能因为网络调用阻塞timingwheel
            processor.run(() -> new DownCmdEvent(frame, conn, false, traceId), traceId);
        } else {
            msgPipeline.sendDown(new DownCmdEvent(frame, conn, false, traceId));
        }
        // 获取重试次数
        int times = msg.getTimes();
        msg.setTimes(++times);
        // 加入重试
        this.addRetry(msg);
    }

    /**
     * 执行定时任务
     */
    public void schedule(long interval, Runnable runnable) {
        this.systemTimer.scheduler(interval, runnable);
    }

    /**
     * 停止
     */
    public void shutdown() {
        this.stop.compareAndSet(false, true);
    }

    /**
     * 是否已经停止
     */
    public boolean getStoped() {
        return this.stop.get();
    }

    private int getShard(String connId) {
        // 根据connId计算落在哪个飞行队列分片上
        int positive = Utils.toPositive(Utils.murmur2(connId.getBytes(StandardCharsets.UTF_8)));
        return positive % retryQueues.length;
    }

    private long getPri(int times) {
        if (times >= maxTimes) {
            times = maxTimes;
        }
        long waitTime = interval * (1L << (times - 1));
        double jitter = 0.1 * Math.random();
        waitTime = (long) (waitTime * (1 + jitter));
        return System.currentTimeMillis() + waitTime;
    }
}
