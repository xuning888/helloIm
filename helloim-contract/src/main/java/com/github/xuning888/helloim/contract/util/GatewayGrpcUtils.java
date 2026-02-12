package com.github.xuning888.helloim.contract.util;


import com.github.xuning888.helloim.api.protobuf.common.v1.Endpoint;
import com.github.xuning888.helloim.api.protobuf.common.v1.FramePb;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateUser;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.gateway.DownMsgSvcClient;
import com.github.xuning888.helloim.contract.gateway.DownMsgSvcClientFactory;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2026/2/8 18:11
 */
public class GatewayGrpcUtils {

    private static final Logger logger = LoggerFactory.getLogger(GatewayGrpcUtils.class);

    public static void pushResponse(MsgContext msgContext, Endpoint endpoint, String traceId) {
        if (msgContext == null || endpoint == null) {
            logger.error("pushResponse msgContext or endpoint is null, traceId: {}", traceId);
            return;
        }
        Frame frame = msgContext.getFrame();
        if (frame == null) {
            logger.error("pushResponse frame is null, traceId: {}", traceId);
            return;
        }
        Header header = frame.getHeader();
        int cmdId = header.getCmdId();
        Frame responseFrame = null;
        if (MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE == cmdId) {
            responseFrame = MsgUtils.c2cSendRequestResponse(msgContext);
        } else {
            responseFrame = MsgUtils.emptyFrame(msgContext);
        }
        String from = msgContext.getMsgFrom();
        Long uidInt64 = null;
        try {
            uidInt64 = Long.parseLong(from);
        } catch (Exception ex) {
            logger.error("pushResponse parseLong error from: {}, traceId: {}", from, traceId);
            return;
        }
        GateUser gateUser = GateUser.newBuilder()
                .setUid(uidInt64).setUserType(msgContext.getFromUserType())
                .setSessionId(msgContext.getSessionId()).build();
        FramePb framePb = FrameUtils.convertToPb(responseFrame);

        pushMessage(framePb, Collections.singletonList(gateUser), endpoint, traceId);
    }

    public static List<GateUser> pushMessage(FramePb frame, List<GateUser> users, Endpoint endpoint, String traceId) {
        DownMsgSvcClient cli = DownMsgSvcClientFactory.getClient(endpoint);
        return cli.pushMessage(frame, users, false, traceId);
    }

    public static List<GateUser> pushMessageNeedAck(FramePb frame, List<GateUser> users, Endpoint endpoint, String traceId) {
        DownMsgSvcClient cli = DownMsgSvcClientFactory.getClient(endpoint);
        return cli.pushMessage(frame, users, true, traceId);
    }
}
