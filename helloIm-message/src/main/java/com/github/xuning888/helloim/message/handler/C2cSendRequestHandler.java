package com.github.xuning888.helloim.message.handler;

import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.ChatMessage;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.kafka.MsgKafkaProducer;
import com.github.xuning888.helloim.contract.kafka.Topics;
import com.github.xuning888.helloim.contract.protobuf.C2cMessage;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.message.rpc.DubboAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xuning
 * @date 2025/8/22 23:40
 */
@Component
public class C2cSendRequestHandler implements MsgHandler {

    private static final Logger logger = LoggerFactory.getLogger(C2cSendRequestHandler.class);

    @Resource
    private DubboAdapter dubboAdapter;

    @Override
    public int getCmdId() {
        return MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE;
    }

    @Override
    public void handleMessage(MsgContext msgContext, String kTraceId) {
        Frame frame = msgContext.getFrame();
        String traceId = msgContext.getTraceId();
        C2cMessage.C2cSendRequest c2cSendRequest = null;
        try {
            c2cSendRequest = C2cMessage.C2cSendRequest.parseFrom(frame.getBody());
        } catch (Exception ex) {
            logger.error("parse c2cSendRequest failed, from: {}, to: {}, traceId: {}",
                    msgContext.getMsgFrom(), msgContext.getMsgTo(), traceId);
            return;
        }

        if (c2cSendRequest == null) {
            logger.error("c2cSendRequest is null, traceId: {}", traceId);
            return;
        }

        // 消息持久化
        if (!saveMessage(msgContext, c2cSendRequest)) {
            logger.error("c2cSendMessage saveMessage error: traceId: {}", traceId);
            return;
        }

        // 构造下行消息, 发送消息
        Frame c2cPushRequestFrame = buildC2cPushRequestFrame(msgContext, c2cSendRequest);
        msgContext.setFrame(c2cPushRequestFrame);
        MsgKafkaProducer.getInstance().send(Topics.C2C.C2C_PUSH_RES, msgKey(msgContext), msgContext);
    }


    private boolean saveMessage(MsgContext msgContext, C2cMessage.C2cSendRequest c2cSendRequest) {
        ChatMessage chatMessage = convertChatMessage(msgContext, c2cSendRequest);
        int raw = this.dubboAdapter.msgStoreService().saveMessage(chatMessage, msgContext.getTraceId());
        return raw > 0;
    }

    private ChatMessage convertChatMessage(MsgContext msgContext, C2cMessage.C2cSendRequest c2cSendRequest) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setCmdId(MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE);
        chatMessage.setChatType(ChatType.C2C.getType());
        chatMessage.setMsgId(msgContext.getMsgId());
        chatMessage.setMsgFrom(Long.parseLong(msgContext.getMsgFrom()));
        chatMessage.setFromUserType(msgContext.getFromUserType());
        chatMessage.setMsgTo(Long.parseLong(msgContext.getMsgTo()));
        chatMessage.setToUserType(msgContext.getToUserType());
        chatMessage.setGroupId(0L);
        chatMessage.setMsgSeq(msgContext.getMsgSeq());
        chatMessage.setMsgContent(c2cSendRequest.getContent());
        chatMessage.setContentType(c2cSendRequest.getContentType());
        chatMessage.setSendTime(new Date()); // TODO 这个时间不准确
        chatMessage.setReceiptStatus(0);
        chatMessage.setServerSeq(msgContext.getServerSeq());
        return chatMessage;
    }

    private String msgKey(MsgContext msgContext) {
        return msgContext.getMsgFrom() + "_" + msgContext.getMsgTo() + "_" + msgContext.getServerSeq();
    }


    private Frame buildC2cPushRequestFrame(MsgContext msgContext, C2cMessage.C2cSendRequest request) {
        C2cMessage.C2cPushRequest c2cPushRequest = buildC2cPushRequest(msgContext, request);
        byte[] body = c2cPushRequest.toByteArray();
        Header header = msgContext.getFrame().getHeader().copy();
        header.setCmdId(MsgCmd.CmdId.CMD_ID_C2CPUSH_VALUE);
        header.setBodyLength(body.length);
        return new Frame(header, body);
    }


    private C2cMessage.C2cPushRequest buildC2cPushRequest(MsgContext msgContext, C2cMessage.C2cSendRequest c2cSendRequest) {
        C2cMessage.C2cPushRequest.Builder builder = C2cMessage.C2cPushRequest.newBuilder();
        builder.setMsgId(msgContext.getMsgId()); // 消息ID
        builder.setFrom(msgContext.getMsgFrom()); // 消息的发送方
        builder.setTo(msgContext.getMsgTo()); // 消息的接收方
        builder.setContent(c2cSendRequest.getContent()); // 消息内容
        builder.setContentType(c2cSendRequest.getContentType()); // 消息类型
        builder.setServerSeq(msgContext.getServerSeq()); // 服务端消息序号
        builder.setTimestamp(msgContext.getTimestamp());
        return builder.build();
    }
}