package com.github.xuning888.helloim.gateway.service;

import com.github.xuning888.helloim.contract.api.request.DownMessageReq;
import com.github.xuning888.helloim.contract.api.service.gate.DownMsgService;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.protobuf.C2cMessage;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuning
 * @date 2025/8/3 13:50
 */
@DubboService
public class DownMsgServiceImpl implements DownMsgService {

    private static final Logger logger = LoggerFactory.getLogger(DownMsgServiceImpl.class);

    @Override
    public void pushMessage(DownMessageReq req) {
        logger.info("pushMessage req: {}", req);
        Frame frame = req.getFrame();
        byte[] body = frame.getBody();
        C2cMessage.C2cSendResponse c2cSendResponse = null;
        try {
            c2cSendResponse = C2cMessage.C2cSendResponse.parseFrom(body);
        } catch (Exception ex) {
            logger.error("error traceId: {}", req.getTraceId(), ex);
            return;
        }
        logger.info("sendSendResponse: {}", c2cSendResponse);
    }
}