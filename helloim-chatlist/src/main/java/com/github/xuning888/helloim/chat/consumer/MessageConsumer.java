package com.github.xuning888.helloim.chat.consumer;

import com.github.xuning888.helloim.chat.component.ChatMessageComponent;
import com.github.xuning888.helloim.chat.handler.MsgHandler;
import com.github.xuning888.helloim.chat.utils.ThreadPoolUtils;
import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.kafka.MsgKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;
import java.util.Properties;

/**
 * @author xuning
 * @date 2025/10/7 15:42
 */
public class MessageConsumer extends MsgKafkaConsumer {

    private final ChatService chatService;

    private final ChatMessageComponent chatMessageComponent;

    public MessageConsumer(ChatService chatService, ChatMessageComponent chatMessageComponent,
                           Properties properties, List<String> topics) {
        super(properties, topics);
        this.chatService = chatService;
        this.chatMessageComponent = chatMessageComponent;
    }

    @Override
    public void processRecord(ConsumerRecord<String, byte[]> record, String traceId) {
        MsgHandler msgHandler = new MsgHandler(record, chatService,
                chatMessageComponent,
                traceId);
        ThreadPoolUtils.requestPool.submit(msgHandler);
    }
}
