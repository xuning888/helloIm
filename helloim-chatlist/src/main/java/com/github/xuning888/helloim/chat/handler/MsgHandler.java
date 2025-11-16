package com.github.xuning888.helloim.chat.handler;

import com.github.xuning888.helloim.chat.component.ChatMessageComponent;
import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.convert.MessageConvert;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.protobuf.C2cMessage;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.ProtoStuffUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuning
 * @date 2025/10/7 15:45
 */
public class MsgHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MsgHandler.class);

    private final ConsumerRecord<String, byte[]> record;
    private final ChatService chatService;
    private final ChatMessageComponent chatMessageComponent;
    private final String traceId;

    public MsgHandler(ConsumerRecord<String, byte[]> record,
                      ChatService chatService,
                      ChatMessageComponent chatMessageComponent,
                      String traceId) {
        this.record = record;
        this.chatService = chatService;
        this.chatMessageComponent = chatMessageComponent;
        this.traceId = traceId;
    }

    @Override
    public void run() {
        MsgContext msgContext = null;
        try {
            msgContext = ProtoStuffUtils.deserialize(record.value(), MsgContext.class);
        } catch (Exception ex) {
            logger.error("deserialize MsgContext error, traceId: {}", traceId, ex);
            return;
        }
        if (msgContext == null) {
            logger.error("msgContext is null, traceId: {}", traceId);
            return;
        }
        Frame frame = msgContext.getFrame();
        int cmdId = frame.getHeader().getCmdId();
        if (MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE == cmdId) {
            processC2c(msgContext);
        } else if (MsgCmd.CmdId.CMD_ID_C2GSEND_VALUE == cmdId) {
            // TODO
        } else {
            logger.warn("unSupport cmdId: {}, traceId: {}", cmdId, traceId);
        }
    }

    // 单聊创建或激活会话
    private void processC2c(MsgContext msgContext) {
        String traceId = msgContext.getTraceId();
        // 消息发送者
        Long msgFrom = Long.parseLong(msgContext.getMsgFrom());
        // 消息接收者
        Long msgTo = Long.parseLong(msgContext.getMsgTo());
        logger.info("processC2c, msgFrom: {}, msgTo: {}, traceId: {}", msgFrom, msgTo, traceId);

        C2cMessage.C2cSendRequest c2cSendRequest = null;
        Frame frame = msgContext.getFrame();
        try {
            c2cSendRequest = C2cMessage.C2cSendRequest.parseFrom(frame.getBody());
        } catch (Exception ex) {
            logger.error("handleMessage parse c2cSendRequest failed, from: {}, to: {}, traceId: {}",
                    msgContext.getMsgFrom(), msgContext.getMsgTo(), traceId);
            return;
        }
        ChatMessageDto chatMessage = MessageConvert.buildC2CChatMessage(msgContext, c2cSendRequest);
        // 更新会话的最后一条消息
        chatMessageComponent.updateLastChatMessage(msgFrom.toString(), msgTo.toString(), chatMessage, traceId);
        // 单聊会话写扩散
        updateC2c(msgFrom, msgTo, traceId);
        updateC2c(msgTo, msgFrom, traceId);
    }

    private void updateC2c(Long from, Long to, String traceId) {
        // 创建或激活会话
        chatService.createOrActivateChat(from, to, ChatType.C2C, traceId);
    }
}
