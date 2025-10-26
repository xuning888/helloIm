package com.github.xuning888.helloim.store.service.impl;

import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.dto.ChatMessage;
import com.github.xuning888.helloim.contract.entity.ImMessage;
import com.github.xuning888.helloim.contract.entity.ImMessageGroup;
import com.github.xuning888.helloim.store.service.ImMessageGroupService;
import com.github.xuning888.helloim.store.service.ImMessageService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author xuning
 * @date 2025/9/20 23:54
 */
@DubboService
public class MsgStoreServiceImpl implements MsgStoreService {

    private static final Logger logger = LoggerFactory.getLogger(MsgStoreServiceImpl.class);

    @Resource
    private ImMessageService imMessageService;

    @Resource
    private ImMessageGroupService imMessageGroupService;

    @Override
    public int saveMessage(ChatMessage chatMessage, String traceId) {
        int raw = 0;
        if (ChatType.C2C.match(chatMessage.getChatType())) {
            ImMessage imMessage = new ImMessage();
            imMessage.setMsgId(chatMessage.getMsgId());
            imMessage.setMsgFrom(chatMessage.getMsgFrom());
            imMessage.setFromUserType(chatMessage.getFromUserType());
            imMessage.setMsgTo(chatMessage.getMsgTo());
            imMessage.setToUserType(chatMessage.getToUserType());
            imMessage.setMsgSeq(chatMessage.getMsgSeq());
            imMessage.setMsgContent(chatMessage.getMsgContent());
            imMessage.setContentType(chatMessage.getContentType());
            imMessage.setCmdId(chatMessage.getCmdId());
            imMessage.setSendTime(chatMessage.getSendTime());
            imMessage.setServerSeq(chatMessage.getServerSeq());
            imMessage.setReceiptStatus(chatMessage.getReceiptStatus());
            raw = this.imMessageService.saveMessage(imMessage, traceId);
        } else if (ChatType.C2G.match(chatMessage.getChatType())) {
            ImMessageGroup imMessageGroup = new ImMessageGroup();
            imMessageGroup.setMsgId(chatMessage.getMsgId());
            imMessageGroup.setMsgFrom(chatMessage.getMsgFrom());
            imMessageGroup.setFromUserType(chatMessage.getFromUserType());
            imMessageGroup.setGroupId(chatMessage.getGroupId());
            imMessageGroup.setMsgSeq(chatMessage.getMsgSeq());
            imMessageGroup.setMsgContent(chatMessage.getMsgContent());
            imMessageGroup.setCmdId(chatMessage.getCmdId());
            imMessageGroup.setSendTime(chatMessage.getSendTime());
            imMessageGroup.setServerSeq(chatMessage.getServerSeq());
            raw = this.imMessageGroupService.saveMessage(imMessageGroup, traceId);
        }
        if (raw > 0) {
            logger.info("saveMessage success, msgId: {}, raw: {}, chatType: {}, traceId: {}",
                    chatMessage.getMsgId(), chatMessage.getChatType(), raw, traceId);
        } else {
            logger.warn("saveMessage failed, msgId: {}, chatType: {}, traceId: {}",
                    chatMessage.getMsgId(), chatMessage.getChatType(), traceId);
        }
        return raw;
    }

    @Override
    public Long maxServerSeq(String from, String to, Integer chatType, String traceId) {
        logger.info("maxServerSeq from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
        Long serverSeq = CommonConstant.ERROR_SERVER_SEQ;;
        if (ChatType.C2C.match(chatType)) {
            serverSeq = this.imMessageService.maxServerSeq(from, to, traceId);
        } else if (ChatType.C2G.match(chatType)) {
            serverSeq = this.imMessageGroupService.maxServerSeq(to, traceId);
        } else {
            logger.error("maxServerSeq, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        logger.info("maxServerSeq from: {}, to: {}, chatType: {}, serverSeq: {}, traceId: {}", from, to, serverSeq, chatType, traceId);
        return serverSeq;
    }
}