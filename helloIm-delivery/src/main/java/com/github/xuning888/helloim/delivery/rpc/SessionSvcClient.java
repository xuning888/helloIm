package com.github.xuning888.helloim.delivery.rpc;


import com.github.xuning888.helloim.contract.api.service.SessionService;
import com.github.xuning888.helloim.contract.meta.GateType;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.meta.ImSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/12/27 19:51
 */
@Component
public class SessionSvcClient {

    private static final Logger logger = LoggerFactory.getLogger(SessionSvcClient.class);

    @DubboReference
    private SessionService sessionService;

    public ImSession getSession(GateUser user, GateType gateType, String traceId) {
        logger.info("getSession user: {}, gateType: {}, traceId:{}", user, gateType, traceId);
        ImSession imSession = null;
        try {
            imSession = sessionService.getSession(user, gateType, traceId);
        } catch (Exception ex) {
            logger.error("getSession error, traceId: {}", traceId, ex);
        }
        return imSession;
    }

    public List<ImSession> batchGetSession(List<GateUser> users, GateType gateType, String traceId) {
        logger.info("batchGetSession users: {}, gateType: {}, traceId:{}", users, gateType, traceId);
        List<ImSession> imSessions = null;
        try {
            imSessions = sessionService.batchGetSession(users, gateType, traceId);
        } catch (Exception ex) {
            logger.error("batchGetSession error, traceId: {}", traceId, ex);
        }
        return imSessions;
    }

    public Map<GateUser, ImSession> batchGetSessionMap(List<GateUser> users, GateType gateType, String traceId) {
        logger.info("batchGetSessionMap users: {}, gateType: {}, traceId:{}", users, gateType, traceId);
        Map<GateUser, ImSession> sessionMap = null;
        try {
            sessionMap = sessionService.batchGetSessionMap(users, gateType, traceId);
        } catch (Exception ex) {
            logger.error("batchGetSessionMap error, traceId: {}", traceId, ex);
        }
        return sessionMap;
    }
}
