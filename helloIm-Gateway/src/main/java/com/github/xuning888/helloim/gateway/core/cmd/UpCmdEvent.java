package com.github.xuning888.helloim.gateway.core.cmd;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.gateway.core.conn.Conn;

/**
 * @author xuning
 * @date 2025/8/10 10:55
 */
public class UpCmdEvent implements CmdEvent {

    private final Frame frame;
    private final Conn conn;
    private final String traceId;

    public UpCmdEvent(Frame frame, Conn conn, String traceId) {
        this.frame = frame;
        this.conn = conn;
        this.traceId = traceId;
    }

    @Override
    public Frame getFrame() {
        return frame;
    }

    @Override
    public String getTraceId() {
        return traceId;
    }

    @Override
    public Conn getConn() {
        return conn;
    }
}
