package com.github.xuning888.helloim.dispatch.service;


import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.dispatch.sender.MessageSender;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/8/2 23:45
 */
@Service
public class DispatchService {


    private static final Logger logger = LoggerFactory.getLogger(DispatchService.class);

    private final Map<Integer, MessageSender> senders = new HashMap<>();

    public DispatchService(List<MessageSender> msgSenders) {
        registerSender(msgSenders);
    }

    private void registerSender(List<MessageSender> senderList) {
        if (CollectionUtils.isEmpty(senderList)) {
            return;
        }
        for (MessageSender msgSender : senderList) {
            int cmdId = msgSender.cmdId();
            if (senders.containsKey(cmdId)) {
                logger.error("duplication cmdId sender, cmdId: {}, sender: {}", cmdId, msgSender);
                throw new IllegalArgumentException("duplicate cmdId sender, cmdId " + cmdId + " " + msgSender);
            }
            senders.put(cmdId, msgSender);
        }
    }

    public void dispatch(MsgContext msgContext, String traceId) {
        if (msgContext == null) {
            logger.error("dispatch msgContext is null, traceId: {}", traceId);
            return;
        }
        Frame frame = msgContext.getFrame();
        int cmdId = frame.getHeader().getCmdId();
        MessageSender messageSender = senders.get(cmdId);
        if (messageSender != null) {
            messageSender.sendMessage(msgContext);
            return;
        }
        logger.error("dispatch unknown cmdId: {}, traceId: {}", cmdId, traceId);
    }
}