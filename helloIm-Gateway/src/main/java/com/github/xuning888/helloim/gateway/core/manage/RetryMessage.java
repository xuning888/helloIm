package com.github.xuning888.helloim.gateway.core.manage;


import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;

/**
 * @author xuning
 * @date 2026/2/12 17:18
 */
public class RetryMessage {

    private long uid;

    /**
     * 数据包
     */
    private Frame frame;

    /**
     * 消息所属的长连接
     */
    private String connId;

    /**
     * 重试次数
     */
    private int times;

    /**
     * 下行客户端seq
     */
    private int seq;

    /**
     * 小根堆的index, 用户将重试消息从队列中快速删除
     */
    private int index;

    /**
     * 重试队列中的优先级
     */
    private long pri;

    private String traceId;

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getPri() {
        return pri;
    }

    public void setPri(long pri) {
        this.pri = pri;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getCmdId() {
        Header header = frame.getHeader();
        return header.getCmdId();
    }
}

