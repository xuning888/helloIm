package com.github.xuning888.helloim.message.handler;

import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.util.ProtoStuffUtils;
import com.github.xuning888.helloim.message.util.ThreadPoolUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/8/22 23:40
 */
@Service
public class MsgDispatchService {

    private static final Logger logger = LoggerFactory.getLogger(MsgDispatchService.class);

    private final Map<Integer, MsgHandler> handlerMap = new HashMap<>();

    public MsgDispatchService(List<MsgHandler> handlers) {
        for (MsgHandler handler : handlers) {
            int cmdId = handler.getCmdId();
            MsgHandler msgHandler = handlerMap.get(cmdId);
            if (msgHandler != null) {
                logger.error("duplicate msgHandler cmdId: {}", cmdId);
                throw new IllegalArgumentException("duplicate msgHandler cmdId " + cmdId);
            }
            handlerMap.put(cmdId, handler);
        }
    }

    public void dispatchMsg(ConsumerRecord<String, byte[]> record, String kTraceId) {
        ThreadPoolUtils.requestPool.submit(new DispatchTask(record, kTraceId));
    }

    private void doDispatch(MsgContext msgContext, String kTraceId) {
        Frame frame = msgContext.getFrame();
        Header header = frame.getHeader();
        int cmdId = header.getCmdId();
        MsgHandler msgHandler = handlerMap.get(cmdId);
        if (msgHandler == null) {
            return;
        }
        msgHandler.handleMessage(msgContext, kTraceId);
    }

    private class DispatchTask implements Runnable {

        private final ConsumerRecord<String, byte[]> record;
        private final String traceId;

        public DispatchTask(ConsumerRecord<String, byte[]> record, String kTraceId) {
            this.record = record;
            this.traceId = kTraceId;
        }

        @Override
        public void run() {
            MsgContext msgContext = null;
            String topic = record.topic();
            String key = record.key();
            long offset = record.offset();
            byte[] value = record.value();
            try {
                msgContext = ProtoStuffUtils.deserialize(value, MsgContext.class);
                MsgDispatchService.this.doDispatch(msgContext, this.traceId);
            } catch (Exception ex) {
                String errMsg = null;
                if (msgContext != null) {
                    logger.error("handle message failed, topic: {}, key: {}, offset: {}, msgId: {}, traceId: {}",
                            topic, key, offset, msgContext.getMsgId(), traceId, ex);
                } else {
                    logger.error("handle message failed, msgContext is null, topic: {}, key: {}, offset: {}, traceId: {}",
                            topic, key, offset, traceId, ex);
                }
            }
        }
    }
}