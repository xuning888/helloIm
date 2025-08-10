package com.github.xuning888.helloim.gateway.core.cmd.handler;

import com.github.xuning888.helloim.contract.api.request.UpMessageReq;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.gateway.adapter.UpMsgServiceAdapter;
import com.github.xuning888.helloim.gateway.config.GateAddr;
import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.CmdHandler;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.session.Session;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuning
 * @date 2025/8/10 13:32
 */
public class DefaultHandler implements CmdHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHandler.class);

    private final UpMsgServiceAdapter upMsgServiceAdapter;
    private final SessionManager   sessionManager;
    private final GateAddr gateAddr;

    public DefaultHandler(UpMsgServiceAdapter upMsgServiceAdapter,
                          SessionManager sessionManager, GateAddr gateAddr) {
        this.upMsgServiceAdapter = upMsgServiceAdapter;
        this.sessionManager = sessionManager;
        this.gateAddr = gateAddr;
    }

    @Override
    public void handle(CmdEvent cmdEvent) throws Exception {
        Frame frame = cmdEvent.getFrame();
        String traceId = cmdEvent.getTraceId();
        Conn conn = cmdEvent.getConn();
        // 投递上行消息到路由层
        Session session = sessionManager.getSession(conn.getId(), traceId);
        if (session == null) {
            // 这种情况就是非法长连接, 回复一个空包
            writeEmpty(cmdEvent);
            logger.error("投递上行消息, 但是conn没有于用户关联, conn: {}, traceId: {}", conn.getId(), traceId);
            return;
        }
        // 传递上行消息到下次层
        GateUser user = session.getUser();
        Endpoint endpoint = gateAddr.endpoint();
        UpMessageReq upMessageReq = new UpMessageReq(frame, endpoint, user, traceId);
        upMsgServiceAdapter.upMsgService().sendMessage(upMessageReq);
    }

    private void writeEmpty(CmdEvent cmdEvent) {
        Frame frame = cmdEvent.getFrame();
        Header header = frame.getHeader();
        Header copy = header.copy();
        copy.setBodyLength(0);
        Frame response = new Frame(copy, new byte[0]);
        cmdEvent.getConn().write(response, cmdEvent.getTraceId());
    }
}