package com.github.xuning888.helloim.chat.handler;

import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.entity.ImChat;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.ProtoStuffUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author xuning
 * @date 2025/10/7 15:45
 */
public class MsgHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MsgHandler.class);

    private final ConsumerRecord<String, byte[]> record;
    private final ChatService chatService;
    private final String traceId;

    public MsgHandler(ConsumerRecord<String, byte[]> record, ChatService chatService, String traceId) {
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

        // 消息发送者的会话
        ImChat imFromChat = new ImChat();
        imFromChat.setUserId(msgFrom);
        imFromChat.setChatId(msgTo);
        imFromChat.setChatType(ChatType.C2C.getType());
        imFromChat.setChatTop(0);
        imFromChat.setChatDel(0);
        imFromChat.setUpdateTimestamp(new Date()); // 更新时间
        imFromChat.setDelTimestamp(new Date()); // 会话删除时间
        imFromChat.setChatMute(0); // 会话静默

        // 构建会话缓存
        chatService.createOrActivateChat(msgFrom, msgFrom, ChatType.C2C, traceId);
        chatService.createOrActivateChat(msgTo, msgFrom, ChatType.C2C, traceId);
    }
}
