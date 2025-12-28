package com.github.xuning888.helloim.delivery.handler;

import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.util.ProtoStuffUtils;
import com.github.xuning888.helloim.delivery.metrics.MetricRegistryService;
import com.github.xuning888.helloim.delivery.util.ThreadPoolUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/8/23 1:10
 */
@Component
public class MsgDispatchService {

    private static final Logger logger = LoggerFactory.getLogger(MsgDispatchService.class);

    private final MetricRegistryService metricRegistryService;

    private final Map<Integer, MsgDeliverHandler> handlerMap = new HashMap<>();

    public MsgDispatchService(List<MsgDeliverHandler> handlers, MetricRegistryService metricRegistryService) {
        for (MsgDeliverHandler handler : handlers) {
            int cmdId = handler.getCmdId();
            MsgDeliverHandler msgDeliverHandler = handlerMap.get(cmdId);
            if (msgDeliverHandler != null) {
                logger.error("duplicate handler cmdId: {}", cmdId);
                throw new IllegalArgumentException("duplicate handler cmdId: " + cmdId);
            }
            handlerMap.put(cmdId, handler);
        }
        this.metricRegistryService = metricRegistryService;
    }

    public void dispatch(ConsumerRecord<String, byte[]> record, String traceId) {
        ThreadPoolUtils.requestPool.submit(new DispatchTask(record, traceId));
    }

    private void doDispatch(MsgContext msgContext, String kTraceId) {
        Frame frame = msgContext.getFrame();
        int cmdId = frame.getHeader().getCmdId();
        MsgDeliverHandler msgDeliverHandler = handlerMap.get(cmdId);
        if (msgDeliverHandler == null) {
            return;
        }
        msgDeliverHandler.handle(msgContext, kTraceId);
        // 记录消息延时
        metricRegistryService.recordLatency(msgContext);
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