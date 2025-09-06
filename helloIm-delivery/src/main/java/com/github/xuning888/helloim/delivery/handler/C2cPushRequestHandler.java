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
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/8/23 1:11
 */
@Component
public class C2cPushRequestHandler implements MsgDeliverHandler {

    @DubboReference
    private SessionService sessionService;

    @Override
    public int getCmdId() {
        return MsgCmd.CmdId.CMD_ID_C2CPUSH_VALUE;
    }

    @Override
    public void handle(MsgContext msgContext, String kTraceId) {
        String msgTo = msgContext.getMsgTo();
        GateUser toUser = new GateUser(Long.parseLong(msgTo));
        ImSession imSession = getSessionTcp(toUser, msgContext.getTraceId());
        if (imSession != null) {
            // 用户在线
            Endpoint endpoint = imSession.getEndpoint();
            GatewayUtils.pushMessage(msgContext.getFrame(), toUser, endpoint, msgContext.getTraceId());
        }
    }

    private ImSession getSessionTcp(GateUser user, String traceId) {
        return sessionService.getSession(user, GateType.TCP, traceId);
    }
}