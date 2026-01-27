package com.github.xuning888.helloim.gateway.service;

import com.github.xuning888.helloim.contract.api.request.DownMessageReq;
import com.github.xuning888.helloim.contract.api.response.DownMessageResp;
import com.github.xuning888.helloim.contract.api.service.DownMsgService;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.session.Session;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xuning
 * @date 2025/8/3 13:50
 */
@DubboService
public class DownMsgServiceImpl implements DownMsgService {

    private static final Logger logger = LoggerFactory.getLogger(DownMsgServiceImpl.class);

    private final SessionManager sessionManager;

    public DownMsgServiceImpl(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public DownMessageResp pushMessage(DownMessageReq req) {
        logger.debug("pushMessage req: {}", req);
        Frame frame = req.getFrame();
        boolean needAck = req.getNeedAck();
        Set<GateUser> offlineUsers = new HashSet<>();
        for (GateUser user : req.getUsers()) {
            if (user == null) {
                logger.error("pushMessage user is null, traceId: {}", req.getTraceId());
                continue;
            }
            String sessionId = user.getSessionId();
            if (StringUtils.isBlank(sessionId)) {
                logger.error("pushMessage sessionId is null, user: {}, traceId: {}", user, req.getTraceId());
                offlineUsers.add(user);
                continue;
            }
            Session session = sessionManager.getSession(sessionId, req.getTraceId());
            if (session == null || !session.isOk()) {
                logger.warn("pushMessage, session is null or not ok, user: {}, traceId: {}", user, req.getTraceId());
                offlineUsers.add(user);
                continue;
            }
            // 用户在线，发送消息
            sendMessage(frame, session, needAck, req.getTraceId());
        }
        return new DownMessageResp(offlineUsers, req.getTraceId());
    }

    /**
     * 将frame写出到peer, 因为conn的write方法是同步写出, 所以方法会阻塞, 可以考虑放到线程池中
     */
    private void sendMessage(Frame frame, Session session, boolean needAck, String traceId) {
        // 投递下行事件
        session.getConn().getMsgPipeline().sendDown(new DownCmdEvent(frame, session.getConn(), needAck, traceId));
    }
}