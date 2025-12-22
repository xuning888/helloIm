package com.github.xuning888.helloim.message.handler;

import com.github.xuning888.helloim.contract.convert.MessageConvert;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.kafka.MsgKafkaProducer;
import com.github.xuning888.helloim.contract.kafka.Topics;
import com.github.xuning888.helloim.contract.protobuf.C2cMessage;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.message.rpc.DubboAdapter;
import com.github.xuning888.helloim.message.service.OfflineMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xuning
 * @date 2025/8/22 23:40
 */
@Component
public class C2cSendRequestHandler implements MsgHandler {

    private static final Logger logger = LoggerFactory.getLogger(C2cSendRequestHandler.class);

    @Resource
    private DubboAdapter dubboAdapter;

    @Resource
    private OfflineMessageService offlineMessageService;

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
            logger.error("handleMessage parse c2cSendRequest failed, from: {}, to: {}, traceId: {}",
                    msgContext.getMsgFrom(), msgContext.getMsgTo(), traceId);
            return;
        }

        if (c2cSendRequest == null) {
            logger.error("handleMessage c2cSendRequest is null, traceId: {}", traceId);
            return;
        }
        logger.info("handleMessage serverSeq: {} traceId: {}", msgContext.getServerSeq(), traceId);

        ChatMessageDto chatMessageDto = MessageConvert.buildC2CChatMessage(msgContext, c2cSendRequest);
        // 消息持久化
        if (!saveMessage(chatMessageDto, traceId)) {
            logger.error("c2cSendMessage saveMessage error: traceId: {}", traceId);
            return;
        }
        // 保存离线消息
        offlineMessageService.saveOfflineMessage(chatMessageDto, traceId);
        // 构造下行消息, 发送消息
        Frame c2cPushRequestFrame = buildC2cPushRequestFrame(msgContext, c2cSendRequest);
        msgContext.setFrame(c2cPushRequestFrame);
        MsgKafkaProducer.getInstance().send(Topics.C2C.C2C_PUSH_RES, msgKey(msgContext), msgContext);
    }


    private boolean saveMessage(ChatMessageDto chatMessageDto, String traceId) {
        int raw = this.dubboAdapter.msgStoreService().saveMessage(chatMessageDto, traceId);
        return raw > 0;
    }

    private String msgKey(MsgContext msgContext) {
        return msgContext.getMsgFrom() + "_" + msgContext.getMsgTo() + "_" + msgContext.getServerSeq();
    }


    private Frame buildC2cPushRequestFrame(MsgContext msgContext, C2cMessage.C2cSendRequest request) {
        C2cMessage.C2cPushRequest c2cPushRequest = buildC2cPushRequest(msgContext, request);
        byte[] body = c2cPushRequest.toByteArray();
        Header header = msgContext.getFrame().getHeader().copy();
        header.setReq(Header.REQ);
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