package com.github.xuning888.helloim.chat.handler;

import com.github.xuning888.helloim.chat.api.Chat;
import com.github.xuning888.helloim.chat.api.service.ChatServiceTriple;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
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
    private final ChatServiceTriple chatService;
    private final String traceId;

    public MsgHandler(ConsumerRecord<String, byte[]> record, ChatServiceTriple chatService, String traceId) {
        this.record = record;
        this.chatService = chatService;
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

        // 构建会话缓存
        chatService.createOrActivateChat(buildC2CRequest(msgFrom, msgTo, traceId));
        chatService.createOrActivateChat(buildC2CRequest(msgTo, msgFrom, traceId));
        // TODO 更新会话最后一条消息
    }


    private Chat.CreateOrActivateChatRequest buildRequest(Long userId, Long chatId, ChatType chatType, String traceId) {
        return Chat.CreateOrActivateChatRequest.newBuilder().setUserId(userId).setChatId(chatId)
                .setChatType(chatType.getType()).setTraceId(traceId).build();
    }

    public Chat.CreateOrActivateChatRequest buildC2CRequest(Long userId, Long chatID, String traceId) {
        return buildRequest(userId, chatID, ChatType.C2C, traceId);
    }
}
