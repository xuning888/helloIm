package com.github.xuning888.helloim.gateway.core.conn.event;

import com.github.xuning888.helloim.gateway.core.conn.Conn;

import java.io.Serializable;

/**
 * 连接状态事件
 * @author xuning
 * @date 2025/8/10 10:57
 */
public class ConnStateEvent implements ConnEvent {

    private final Conn conn;
    private final State state;
    private final String traceId;

    public ConnStateEvent(Conn conn, State state, String traceId) {
        this.conn = conn;
        this.state = state;
        this.traceId =traceId;
    }

    @Override
    public Conn getConn() {
        return conn;
    }

    public State getState() {
        return this.state;
    }

    @Override
    public String getTraceId() {
        return this.traceId;
    }

    public State state() {
        return this.state;
    }

    public enum State implements Serializable {
        OPEN, CLOSE, TIMEOUT, ERROR
    }
}
