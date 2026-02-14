package com.github.xuning888.helloim.gateway.core.manage;


import com.github.xuning888.helloim.contract.frame.Header;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuning
 * @date 2026/2/12 18:44
 */
public class RetryQueue {

    private final InFlightPqueue inFlightPqueue;
    private final Map<String, RetryMessage> inFlightMessages;
    private final ReentrantLock lock;
    private final RetryManager retryManager;
    private final long scanIntervalMs;

    public RetryQueue(RetryManager retryManager, long scanIntervalMs) {
        this.scanIntervalMs = scanIntervalMs;
        this.retryManager = retryManager;
        this.lock = new ReentrantLock();
        this.inFlightPqueue = new InFlightPqueue(128);
        this.inFlightMessages = new HashMap<>(128);
    }

    public void startInFlightTimeout(String connId, RetryMessage msg) {
        if (msg == null) {
            return;
        }
        lock.lock();
        try {
            pushToInFlightMessages(connId, msg);
            addInFlightPqueue(msg);
        } finally {
            lock.unlock();
        }
    }

    public boolean removeMessage(String connId, int seq, int cmdId) {
        String key = msgKey(connId, seq, cmdId);
        lock.lock();
        try {
            RetryMessage retryMessage = inFlightMessages.get(key);
            if (retryMessage == null) {
                return false;
            }
            int index = retryMessage.getIndex();
            inFlightPqueue.remove(index);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void start() {
        this.retryManager.schedule(scanIntervalMs, () -> {
            long now = System.currentTimeMillis();
            this.processInFlightQueue(now);
        });
    }

    private void processInFlightQueue(long time) {
        while (!retryManager.getStoped()) {
            RetryMessage msg = null;
            lock.lock();
            try {
                // 从小根堆里找一个到期的消息
                msg = this.inFlightPqueue.peekAndShift(time);
                if (msg != null) {
                    String key = msgKey(msg.getConnId(), msg.getSeq(), msg.getCmdId());
                    inFlightMessages.remove(key);
                }
            } finally {
                lock.unlock();
            }
            // 没有到期的消息就break掉
            if (msg == null) {
                break;
            }
            this.retryManager.processRetry(msg);
        }
    }

    private void pushToInFlightMessages(String connId, RetryMessage msg) {
        lock.lock();
        try {
            Header header = msg.getFrame().getHeader();
            String key = msgKey(connId, header.getSeq(), header.getCmdId());
            RetryMessage retryMessage = inFlightMessages.get(key);
            if (retryMessage != null) {
                return;
            }
            inFlightMessages.put(key, msg);
        } finally {
            lock.unlock();
        }
    }

    private void addInFlightPqueue(RetryMessage msg) {
        lock.lock();
        try {
            this.inFlightPqueue.push(msg);
        } finally {
            lock.unlock();
        }
    }

    private String msgKey(String connId, int seq, int cmdId) {
        return connId + "_" + seq + "_" + cmdId;
    }
}
