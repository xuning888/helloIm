package com.github.xuning888.helloim.gateway.core.cmd.handler;

import com.github.xuning888.helloim.api.protobuf.common.v1.Endpoint;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateUser;
import com.github.xuning888.helloim.api.protobuf.gateway.v1.UpMessageRequest;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.util.FrameUtils;
import com.github.xuning888.helloim.gateway.config.GateAddr;
import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.CmdHandler;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.manage.RetryManager;
import com.github.xuning888.helloim.gateway.core.session.Session;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import com.github.xuning888.helloim.gateway.rpc.UpMsgServiceRpc;
import com.github.xuning888.helloim.gateway.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuning
 * @date 2025/8/10 13:32
 */
public class DefaultHandler implements CmdHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHandler.class);

    private final UpMsgServiceRpc upMsgServiceRpc;
    private final SessionManager  sessionManager;
    private final RetryManager retryManager;
    private final GateAddr gateAddr;

    public DefaultHandler(UpMsgServiceRpc UpMsgServiceRpc,
                          SessionManager sessionManager,
                          RetryManager retryManager,
                          GateAddr gateAddr) {
        this.upMsgServiceRpc = UpMsgServiceRpc;
        this.sessionManager = sessionManager;
        this.retryManager = retryManager;
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
            CommonUtils.writeEmpty(cmdEvent);
            logger.error("投递上行消息, 但是conn没有于用户关联, conn: {}, traceId: {}", conn.getId(), traceId);
            return;
        }
        // 尝试ACK
        GateUser user = session.getUser();
        Header header = frame.getHeader();
        if (header.getReq() == Header.RES) {
            String connId = conn.getId();
            retryManager.ack(connId, user.getUid(), header.getSeq(), header.getCmdId());
            return;
        }
        // 传递上行消息到下次层
        Endpoint endpoint = gateAddr.endpoint();
        UpMessageRequest request = UpMessageRequest.newBuilder().setFrame(FrameUtils.convertToPb(frame))
                .setEndpoint(endpoint).setGateUser(user).setTraceId(traceId).build();
        upMsgServiceRpc.sendMessage(request);
    }
}