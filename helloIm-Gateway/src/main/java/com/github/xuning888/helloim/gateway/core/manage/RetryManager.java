package com.github.xuning888.helloim.gateway.core.manage;


import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.util.timer.SystemTimer;
import com.github.xuning888.helloim.contract.util.timer.SystemTimerReaper;
import com.github.xuning888.helloim.gateway.config.GateServerProperties;
import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import com.github.xuning888.helloim.gateway.core.processor.Processor;
import com.github.xuning888.helloim.gateway.core.session.Session;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
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
    private final SystemTimerReaper timer;
    private final SessionManager sessionManager;
    private final AtomicBoolean stop;

    public RetryManager(SessionManager sessionManager, GateServerProperties.Retry retry) {
        this.stop = new AtomicBoolean(false);
        this.sessionManager = sessionManager;
        this.interval = retry.getIntervalMs();
        this.maxTimes = retry.getMaxTimes();
        this.timer = new SystemTimerReaper("RetryManagerReaper", new SystemTimer("RetryManager"));
        retryQueues = new RetryQueue[retry.getWorkerCount()];
        for (int i = 0; i < retry.getWorkerCount(); i++) {
            RetryQueue retryQueue = new RetryQueue(this, retry.getScanIntervalMs());
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
        if (times > maxTimes) {
            return;
        }
        int shard = getShard(msg.getUid());
        msg.setPri(getPri(msg.getTimes()));
        retryQueues[shard].startInFlightTimeout(msg);
    }

    /**
     * 下行消息ACK,取消飞行队列中的重试消息
     */
    public void ack(String connId, long uid, int seq, int cmdId) {
        if (stop.get()) {
            return;
        }
        int shard = getShard(uid);
        retryQueues[shard].removeMessage(connId, uid, seq, cmdId);
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
        int times = msg.getTimes();
        logger.info("processRetry connId: {}, seq:{}, cmdId: {}, times: {}", msg.getConnId(), msg.getSeq(), msg.getCmdId(), times);
        String traceId = msg.getTraceId();
        String connId = msg.getConnId();
        // 获取用户session, 获取不到就直接return, 丢弃重试消息
        Session session = sessionManager.getSession(connId, traceId);
        if (session == null) {
            logger.warn("processRetry, getSession is null, sessionId: {}, traceId: {}", connId, traceId);
            return;
        }

        // 比较重试消息的uid和session的uid, 如果不同就不能发送.
        if (!Objects.equals(msg.getUid(), session.getUser().getUid())) {
            logger.warn("processRetry, uid not equals, msgUid: {}, sessionUid: {}, traceId:{}", msg.getUid(), session.getUser().getUid(), traceId);
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
            processor.run(() -> new DownCmdEvent(frame, conn, traceId), traceId);
        } else {
            msgPipeline.sendDown(new DownCmdEvent(frame, conn, traceId));
        }
        msg.setTimes(++times);
        // 加入重试
        this.addRetry(msg);
    }

    /**
     * 执行定时任务
     */
    public void schedule(long interval, Runnable runnable) {
        this.timer.scheduler(interval, runnable);
    }

    public void start() {
        for (RetryQueue retryQueue : retryQueues) {
            retryQueue.start();
        }
    }

    /**
     * 停止
     */
    public void shutdown() {
        this.stop.compareAndSet(false, true);
        try {
            timer.close();
        } catch (Exception ex) {
            logger.error("close timer error, errMsg:{}", ex.getMessage(), ex);
        }
    }

    /**
     * 是否已经停止
     */
    public boolean getStoped() {
        return this.stop.get();
    }

    private int getShard(long uid) {
        return (int) (uid % retryQueues.length);
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
