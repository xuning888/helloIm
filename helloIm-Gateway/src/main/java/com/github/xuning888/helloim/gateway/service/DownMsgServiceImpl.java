package com.github.xuning888.helloim.gateway.service;

import com.github.xuning888.helloim.contract.api.request.DownMessageReq;
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

/**
 * @author xuning
 * @date 2025/8/3 13:50
 */
@DubboService(
        parameters = {
                "proxyMode", "${im.gate.proxy-mode}",
                "protocol", "${im.gate.protocol}",
                "proxyAddr", "${im.gate.proxy-addr}",
                "webhookAddr", "${im.gate.webhook-addr}"
        }
)
public class DownMsgServiceImpl implements DownMsgService {

    private static final Logger logger = LoggerFactory.getLogger(DownMsgServiceImpl.class);

    private final SessionManager sessionManager;

    public DownMsgServiceImpl(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void pushMessage(DownMessageReq req) {
        logger.debug("pushMessage req: {}", req);
        Frame frame = req.getFrame();
        boolean needAck = req.getNeedAck();
        for (GateUser user : req.getUsers()) {
            sendMessage(frame, user,  needAck, req.getTraceId());
        }
    }

    /**
     * 将frame写出到peer, 因为conn的write方法是同步写出, 所以方法会阻塞, 可以考虑放到线程池中
     */
    private void sendMessage(Frame frame, GateUser user, boolean needAck, String traceId) {
        if (user == null) {
            logger.error("pushMessage user is null, traceId: {}", traceId);
            return;
        }
        String sessionId = user.getSessionId();
        if (StringUtils.isBlank(sessionId)) {
            logger.error("pushMessage sessionId is null, user: {}, traceId: {}", user, traceId);
            return;
        }
        Session session = sessionManager.getSession(sessionId, traceId);
        if (session == null) {
            logger.error("pushMessage, session is null, user: {}, traceId: {}", user, traceId);
            return;
        }
        if (session.isOk()) {
            // 投递下行事件
            session.getConn().getMsgPipeline().sendDown(new DownCmdEvent(frame, session.getConn(), needAck, traceId));
        }
    }
}