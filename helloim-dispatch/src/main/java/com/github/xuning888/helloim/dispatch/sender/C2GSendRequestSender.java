package com.github.xuning888.helloim.dispatch.sender;


import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.kafka.MsgKafkaProducer;
import com.github.xuning888.helloim.contract.kafka.Topics;
import com.github.xuning888.helloim.contract.protobuf.C2gMessage;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.GatewayUtils;
import com.github.xuning888.helloim.dispatch.util.UpMessageUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/12/26 00:02
 */
@Component
public class C2GSendRequestSender implements MessageSender {

    private static final Logger logger = LoggerFactory.getLogger(C2GSendRequestSender.class);

    @DubboReference
    private ChatService chatService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public int cmdId() {
        return MsgCmd.CmdId.CMD_ID_C2GSEND_VALUE;
    }

    @Override
    public void sendMessage(MsgContext msgContext) {
        String traceId = msgContext.getTraceId();
        Frame frame = msgContext.getFrame();
        byte[] body = frame.getBody();
        C2gMessage.C2GSendRequest c2GSendRequest = extraC2gRequest(body, traceId);
        if (c2GSendRequest == null) {
            logger.error("c2gSendRequest 反序列化后消息内容为空, traceId: {}", traceId);
            return;
        }
        String msgFrom = c2GSendRequest.getFrom();
        long groupId = c2GSendRequest.getGroupId();
        msgContext.setGroupId(groupId);
        Long serverSeq = null;
        try {
            serverSeq = chatService.serverSeq(msgFrom, String.valueOf(groupId), ChatType.C2G, traceId);
            if (Objects.equals(serverSeq, CommonConstant.ERROR_SERVER_SEQ)) {
                logger.error("c2gSendRequest 获取serverSeq异常, from: {}, groupId: {}, traceId: {}", msgFrom, groupId, traceId);
                UpMessageUtils.deleteDuplicate(redisTemplate, msgContext);
            }
            // 设置serverSeq
            msgContext.setServerSeq(serverSeq);
        } catch (Exception ex) {
            logger.error("c2gSendRequest 获取serverSeq 异常, traceId: {} ", traceId, ex);
            UpMessageUtils.deleteDuplicate(redisTemplate, msgContext);
            return;
        }

        // 推送消息到kafka
        MsgKafkaProducer.getInstance().send(Topics.C2G.C2G_SEND_REQ, kafkaKey(msgContext), msgContext);

        // 回复ACK
        GatewayUtils.pushResponse(msgContext, msgContext.getEndpoint(), traceId);
    }

    private String kafkaKey(MsgContext msgContext) {
        String msgFrom = msgContext.getMsgFrom();
        return "c2g_sendRequest_" + msgFrom + "_" + msgContext.getGroupId();
    }

    private C2gMessage.C2GSendRequest extraC2gRequest(byte[] body, String traceId) {
        C2gMessage.C2GSendRequest c2gSendRequest = null;
        try {
            c2gSendRequest = C2gMessage.C2GSendRequest.parseFrom(body);
        } catch (Exception ex) {
            logger.error("c2gSendRequest 反序列化消息内容失败, traceId: {}", traceId, ex);
        }
        return c2gSendRequest;
    }
}
