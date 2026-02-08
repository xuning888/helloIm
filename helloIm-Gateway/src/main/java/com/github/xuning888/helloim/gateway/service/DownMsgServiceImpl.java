package com.github.xuning888.helloim.gateway.service;

import com.github.xuning888.helloim.api.protobuf.common.v1.FramePb;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateUser;
import com.github.xuning888.helloim.api.protobuf.gateway.v1.DownMessageRequest;
import com.github.xuning888.helloim.api.protobuf.gateway.v1.DownMessageResponse;
import com.github.xuning888.helloim.api.protobuf.gateway.v1.DubboDownMsgServiceTriple;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.util.FrameUtils;
import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.session.Session;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xuning
 * @date 2025/8/3 13:50
 */
@DubboService
public class DownMsgServiceImpl extends DubboDownMsgServiceTriple.DownMsgServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DownMsgServiceImpl.class);

    private final SessionManager sessionManager;

    public DownMsgServiceImpl(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public DownMessageResponse pushMessage(DownMessageRequest request) {
        logger.debug("pushMessage request: {}", request);
        FramePb framePb = request.getFrame();
        String traceId = request.getTraceId();
        boolean needAck = request.getNeedAck();
        List<GateUser> users = request.getUsersList();
        Set<GateUser> offlineUsers = new HashSet<>();
        for (GateUser user : users) {
            if (user == null) {
                logger.error("pushMessage user is null, traceId: {}", traceId);
                continue;
            }
            String sessionId = user.getSessionId();
            if (StringUtils.isBlank(sessionId)) {
                logger.error("pushMessage sessionId is null, user: {}, traceId: {}", user, traceId);
                offlineUsers.add(user);
                continue;
            }
            Session session = sessionManager.getSession(sessionId, traceId);
            if (session == null || !session.isOk()) {
                logger.warn("pushMessage, session is null or not ok, user: {}, traceId: {}", user, traceId);
                offlineUsers.add(user);
                continue;
            }
            // 用户在线，发送消息
            Frame frame = FrameUtils.convertToFrame(framePb);
            sendMessage(frame, session, needAck, traceId);
        }
        return DownMessageResponse.newBuilder().addAllOfflineUsers(offlineUsers).build();
    }

    /**
     * 将frame写出到peer, 因为conn的write方法是同步写出, 所以方法会阻塞, 可以考虑放到线程池中
     */
    private void sendMessage(Frame frame, Session session, boolean needAck, String traceId) {
        // 投递下行事件
        session.getConn().getMsgPipeline().sendDown(new DownCmdEvent(frame, session.getConn(), needAck, traceId));
    }
}