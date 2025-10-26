package com.github.xuning888.helloim.dispatch.sender;

import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.kafka.MsgKafkaProducer;
import com.github.xuning888.helloim.contract.kafka.Topics;
import com.github.xuning888.helloim.contract.protobuf.C2cMessage;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.GatewayUtils;
import com.github.xuning888.helloim.dispatch.util.UpMessageUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xuning
 * @date 2025/8/2 22:28
 */
@Component
public class C2CSendRequestSender implements MessageSender {

    private static final Logger logger = LoggerFactory.getLogger(C2CSendRequestSender.class);

    @DubboReference
    private ChatService chatService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public int cmdId() {
        return MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE;
    }

    @Override
    public void sendMessage(MsgContext msgContext) {
        String traceId = msgContext.getTraceId();
        Frame frame = msgContext.getFrame();
        byte[] body = frame.getBody();

        // c2c上行消息反序列化
        C2cMessage.C2cSendRequest c2cSendRequest = extraC2cSendRequest(body, traceId);
        if (c2cSendRequest == null) {
            logger.error("c2cSendRequest 反序列化后消息内容为空, traceId: {}", traceId);
            return;
        }

        String msgFrom = c2cSendRequest.getFrom();
        String msgTo = c2cSendRequest.getTo();
        int toUserType = c2cSendRequest.getToUserType();
        // 设置消息的接收方
        msgContext.setMsgTo(msgTo);
        msgContext.setToUserType(toUserType);

        // 生成serverSeq
        Long serverSeq = null;
        try {
            serverSeq = chatService.serverSeq(msgFrom, msgTo, ChatType.C2C, traceId);
            if (Objects.equals(serverSeq, CommonConstant.ERROR_SERVER_SEQ)) {
                logger.error("c2cSendRequest 获取serverSeq异常, from: {}, to: {}, traceId: {}", msgFrom, msgTo, traceId);
                UpMessageUtils.deleteDuplicate(redisTemplate, msgContext);
                return;
            }
            // 设置serverSeq
            msgContext.setServerSeq(serverSeq);
        } catch (Exception ex) {
            logger.error("s2cSendRequest ");
            UpMessageUtils.deleteDuplicate(redisTemplate, msgContext);
            return;
        }

        // 推送消息到kafka
        MsgKafkaProducer.getInstance().send(Topics.C2C.C2C_SEND_REQ, kafkaKey(msgContext), msgContext);

        // 回复ACK
        GatewayUtils.pushResponse(msgContext, msgContext.getEndpoint(), traceId);
    }

    private C2cMessage.C2cSendRequest extraC2cSendRequest(byte[] body, String traceId) {
        C2cMessage.C2cSendRequest c2cSendRequest = null;
        try {
            c2cSendRequest = C2cMessage.C2cSendRequest.parseFrom(body);
        } catch (Exception ex) {
            logger.error("c2cSendRequest 反序列化消息内容失败, traceId: {}", traceId, ex);
        }
        return c2cSendRequest;
    }

    private String kafkaKey(MsgContext msgContext) {
        String msgFrom = msgContext.getMsgFrom();
        return "c2c_sendRequest_" + msgFrom + "_" + msgContext.getMsgTo();
    }
}