package com.github.xuning888.helloim.gateway.core.cmd;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.gateway.core.conn.Conn;

/**
 * @author xuning
 * @date 2025/8/10 11:04
 */
public class DownCmdEvent implements CmdEvent {

    private final Frame frame;
    private final Conn conn;
    private final String traceId;
    private final boolean needAck;

    public DownCmdEvent(Frame frame, Conn conn,  boolean needAck, String traceId) {
        this.frame = frame;
        this.conn = conn;
        this.needAck = needAck;
        this.traceId = traceId;
    }

    @Override
    public Frame getFrame() {
        return frame;
    }

    @Override
    public Conn getConn() {
        return conn;
    }

    @Override
    public String getTraceId() {
        return this.traceId;
    }

    public boolean getNeedAck() {
        return needAck;
    }
}
