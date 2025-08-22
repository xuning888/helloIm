package com.github.xuning888.helloim.gateway.core.cmd.handler;

import com.github.xuning888.helloim.contract.api.request.AuthRequest;
import com.github.xuning888.helloim.contract.api.request.LogoutRequest;
import com.github.xuning888.helloim.contract.api.response.AuthResponse;
import com.github.xuning888.helloim.contract.contant.GateSessionEvent;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.protobuf.Auth;
import com.github.xuning888.helloim.gateway.adapter.UpMsgServiceAdapter;
import com.github.xuning888.helloim.gateway.config.GateAddr;
import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.CmdHandler;
import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.conn.event.ConnStateEvent;
import com.github.xuning888.helloim.gateway.core.session.Session;
import com.github.xuning888.helloim.gateway.core.session.SessionListener;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuning
 * @date 2025/8/15 1:26
 */
public class AuthHandler implements CmdHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    private final UpMsgServiceAdapter upMsgServiceAdapter;
    private final SessionManager sessionManager;
    private final GateAddr gateAddr;

    public AuthHandler(UpMsgServiceAdapter upMsgServiceAdapter,
                       SessionManager sessionManager, GateAddr gateAddr) {
        this.upMsgServiceAdapter = upMsgServiceAdapter;
        this.sessionManager = sessionManager;
        this.gateAddr = gateAddr;
    }

    @Override
    public void handle(CmdEvent cmdEvent) throws Exception {
        AuthResponse authResponse = handleAuth(cmdEvent);
        if (authResponse.getSuccess()) {
            Session session = createSession(cmdEvent.getConn(), authResponse.getGateUser(), cmdEvent.getTraceId());
            sessionManager.putSession(session, cmdEvent.getTraceId());
            // 构造auth的Response
            Frame response = buildAuthResponse(cmdEvent.getFrame(), authResponse.getGateUser());
            DownCmdEvent downCmdEvent = new DownCmdEvent(response, cmdEvent.getConn(), cmdEvent.getTraceId());
            // 发送下行消息
            cmdEvent.getConn().getMsgPipeline().sendDown(downCmdEvent);
        } else {
            // 认证失败, 关闭连接
            ConnStateEvent connStateEvent = new ConnStateEvent(cmdEvent.getConn(), ConnStateEvent.State.ERROR, cmdEvent.getTraceId());
            sessionManager.changeState(connStateEvent);
        }
    }

    /**
     * 处理Auth信令
     */
    private AuthResponse handleAuth(CmdEvent cmdEvent) {
        Frame frame = cmdEvent.getFrame();
        Conn conn = cmdEvent.getConn();
        String traceId = cmdEvent.getTraceId();
        Auth.AuthRequest authRequest = null;
        try {
            authRequest = Auth.AuthRequest.parseFrom(frame.getBody());
        } catch (Exception ex) {
            logger.error("handleAuth, parse AuthRequest failed, sessionId: {}, traceId: {}", conn.getId(), traceId);
            return new AuthResponse(false, "parse AuthRequest failed", null, traceId);
        }
        if (authRequest == null) {
            logger.error("handleAuth, authRequest is null, sessionId: {}, traceId: {}", conn.getId(), traceId);
            return new AuthResponse(false, "parse AuthRequest failed", null, traceId);
        }

        // 构造authRequest
        AuthRequest logicAuthRequest = new AuthRequest();
        logicAuthRequest.setEndpoint(gateAddr.endpoint());
        logicAuthRequest.setGateUser(new GateUser(Long.parseLong(authRequest.getUid()), authRequest.getUserType()));
        logicAuthRequest.setSessionId(conn.getId());
        logicAuthRequest.setTraceId(cmdEvent.getTraceId());
        // 发送auth到logic
        return upMsgServiceAdapter.upMsgService().auth(logicAuthRequest);
    }


    private Frame buildAuthResponse(Frame frame, GateUser gateUser) {
        Auth.AuthResponse.Builder builder = Auth.AuthResponse.newBuilder();
        builder.setUid(gateUser.getUid().toString());
        builder.setUserType(gateUser.getUserType());
        builder.setSuccess(true);
        Auth.AuthResponse authResponse = builder.build();
        byte[] body = authResponse.toByteArray();

        Header header = frame.getHeader().copy();
        header.setBodyLength(body.length);
        return new Frame(header, body);
    }


    private Session createSession(Conn conn, GateUser gateUser, String traceId) {
        Session session = new Session(conn, gateUser);
        session.addListener(new SessionListener() {
            @Override
            public void notifyEvent(Session session, GateSessionEvent sessionEvent) {
                LogoutRequest logoutRequest = new LogoutRequest();
                logoutRequest.setTraceId(traceId);
                logoutRequest.setGateUser(session.getUser());
                logoutRequest.setSessionEvent(sessionEvent);
                logoutRequest.setEndpoint(gateAddr.endpoint());
                upMsgServiceAdapter.upMsgService().logout(logoutRequest);
            }
        });
        return session;
    }
}