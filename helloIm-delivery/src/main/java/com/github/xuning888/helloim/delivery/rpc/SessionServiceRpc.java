package com.github.xuning888.helloim.delivery.rpc;


import com.github.xuning888.helloim.api.protobuf.common.v1.GateType;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateUser;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImSession;
import com.github.xuning888.helloim.api.protobuf.session.v1.MultiGetImSessionRequest;
import com.github.xuning888.helloim.api.protobuf.session.v1.MultiGetImSessionResponse;
import com.github.xuning888.helloim.api.protobuf.session.v1.SessionService;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuning
 * @date 2026/2/8 18:55
 */
@Component
public class SessionServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceRpc.class);

    @DubboReference
    private SessionService sessionService;

    public ImSession getSession(GateUser user, GateType gateType, String traceId) {
        logger.info("getSession user: {}, gateType: {}, traceId:{}", user, gateType, traceId);
        try {
            List<ImSession> imSessions = this.batchGetSession(Collections.singletonList(user), gateType, traceId);
            if (CollectionUtils.isNotEmpty(imSessions)) {
                return imSessions.get(0);
            }
        } catch (Exception ex) {
            logger.error("getSession error, traceId: {}", traceId, ex);
        }
        return null;
    }

    public List<ImSession> batchGetSession(List<GateUser> users, GateType gateType, String traceId) {
        logger.info("batchGetSession users: {}, gateType: {}, traceId:{}", users, gateType, traceId);
        try {
            MultiGetImSessionRequest request = MultiGetImSessionRequest.newBuilder().setGateType(gateType).setTraceId(traceId)
                    .addAllUsers(users).build();
            MultiGetImSessionResponse response = sessionService.multiGetSession(request);
            return response.getImSessionsList();
        } catch (Exception ex) {
            logger.error("batchGetSession error, traceId: {}", traceId, ex);
            return Collections.emptyList();
        }
    }

    public Map<GateUser, ImSession> batchGetSessionMap(List<GateUser> users, GateType gateType, String traceId) {
        logger.info("batchGetSessionMap users: {}, gateType: {}, traceId:{}", users, gateType, traceId);
        Map<GateUser, ImSession> sessionMap = new HashMap<>();
        try {
            List<ImSession> imSessions = this.batchGetSession(users, gateType, traceId);
            for (ImSession imSession : imSessions) {
                GateUser gateUser = imSession.getGateUser();
                sessionMap.put(gateUser, imSession);
            }
        } catch (Exception ex) {
            logger.error("batchGetSessionMap error, traceId: {}", traceId, ex);
        }
        return sessionMap;
    }
}
