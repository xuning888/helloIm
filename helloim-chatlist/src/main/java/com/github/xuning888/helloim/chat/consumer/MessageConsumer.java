package com.github.xuning888.helloim.chat.consumer;

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

    public MessageConsumer(ChatService chatService, Properties properties, List<String> topics) {
        super(properties, topics);
        this.chatService = chatService;
    }

    @Override
    public void processRecord(ConsumerRecord<String, byte[]> record, String traceId) {
        MsgHandler msgHandler = new MsgHandler(record, chatService, traceId);
        ThreadPoolUtils.requestPool.submit(msgHandler);
    }
}
