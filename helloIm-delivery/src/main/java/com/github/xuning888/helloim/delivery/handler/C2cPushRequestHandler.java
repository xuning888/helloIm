package com.github.xuning888.helloim.delivery.handler;

import com.github.xuning888.helloim.contract.api.service.SessionService;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateType;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.meta.ImSession;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.GatewayUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/8/23 1:11
 */
@Component
public class C2cPushRequestHandler implements MsgDeliverHandler {

    private static final Logger logger = LoggerFactory.getLogger(C2cPushRequestHandler.class);

    @DubboReference
    private SessionService sessionService;

    @Override
    public int getCmdId() {
        return MsgCmd.CmdId.CMD_ID_C2CPUSH_VALUE;
    }

    @Override
    public void handle(MsgContext msgContext, String kTraceId) {
        String msgTo = msgContext.getMsgTo();
        GateUser toUser = new GateUser(Long.parseLong(msgTo), msgContext.getToUserType(), null);
        ImSession imSession = getSessionTcp(toUser, msgContext.getTraceId());
        if (imSession != null) {
            // 用户在线
            GateUser gateUser = imSession.getGateUser();
            Endpoint endpoint = imSession.getEndpoint();
            logger.info("handle message, session: {}, toUser: {}, traceId: {}, kTraceId: {}", imSession, gateUser,
                    msgContext.getTraceId(), kTraceId);
            GatewayUtils.pushMessage(msgContext.getFrame(), gateUser, endpoint, msgContext.getTraceId());
        }
    }

    private ImSession getSessionTcp(GateUser user, String traceId) {
        return sessionService.getSession(user, GateType.TCP, traceId);
    }
}