package com.github.xuning888.helloim.contract.util;

import com.github.xuning888.helloim.contract.api.request.DownMessageReq;
import com.github.xuning888.helloim.contract.api.service.DownMsgService;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2025/8/3 13:01
 */
public class GatewayUtils {

    private static final Logger logger = LoggerFactory.getLogger(GatewayUtils.class);

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
        GateUser gateUser = new GateUser(uidInt64);
        pushMessage(responseFrame, gateUser, endpoint, traceId);
    }

    public static void pushMessage(Frame frame, GateUser gateUser, Endpoint endpoint, String traceId) {
        String appName = ApplicationModel.getApplicationConfig().getName();
        DownMsgService downMsgService = DubboUtils.downMsgService(appName, endpoint, traceId);
        if (downMsgService == null) {
            logger.error("pushMessage error, downMsgService is null, traceId: {}", traceId);
            return;
        }
        DownMessageReq downMessageReq = new DownMessageReq(frame, Collections.singletonList(gateUser), traceId);
        downMsgService.pushMessage(downMessageReq);
    }

    public static void batchPushMessage(Frame frame, List<GateUser> users, Endpoint endpoint, String traceId){
        String appName = ApplicationModel.getApplicationConfig().getName();
        DownMsgService downMsgService = DubboUtils.downMsgService(appName, endpoint, traceId);
        if (downMsgService == null) {
            logger.error("batchPushMessage error, downMsgService is null, traceId: {}", traceId);
            return;
        }
        DownMessageReq downMessageReq = new DownMessageReq(frame, users, traceId);
        downMsgService.pushMessage(downMessageReq);
    }
}