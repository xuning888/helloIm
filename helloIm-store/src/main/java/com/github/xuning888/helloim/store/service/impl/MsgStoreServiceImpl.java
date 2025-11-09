package com.github.xuning888.helloim.store.service.impl;

import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.convert.MessageConvert;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
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
    public int saveMessage(ChatMessageDto chatMessageDto, String traceId) {
        int raw = 0;
        if (ChatType.C2C.match(chatMessageDto.getChatType())) {
            ImMessage imMessage = MessageConvert.convertImMessage(chatMessageDto);
            raw = this.imMessageService.saveMessage(imMessage, traceId);
        } else if (ChatType.C2G.match(chatMessageDto.getChatType())) {
            ImMessageGroup imMessageGroup = MessageConvert.convertImMessageGroup(chatMessageDto);
            raw = this.imMessageGroupService.saveMessage(imMessageGroup, traceId);
        }
        if (raw > 0) {
            logger.info("saveMessage success, msgId: {}, raw: {}, chatType: {}, traceId: {}",
                    chatMessageDto.getMsgId(), chatMessageDto.getChatType(), raw, traceId);
        } else {
            logger.warn("saveMessage failed, msgId: {}, chatType: {}, traceId: {}",
                    chatMessageDto.getMsgId(), chatMessageDto.getChatType(), traceId);
        }
        return raw;
    }

    @Override
    public Long maxServerSeq(String from, String to, Integer chatType, String traceId) {
        logger.info("maxServerSeq from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
        Long serverSeq = null;
        if (ChatType.C2C.match(chatType)) {
            serverSeq = this.imMessageService.maxServerSeq(from, to, traceId);
        } else if (ChatType.C2G.match(chatType)) {
            serverSeq = this.imMessageGroupService.maxServerSeq(to, traceId);
        } else {
            logger.error("maxServerSeq, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        if (serverSeq == null) {
            serverSeq = 0L;
        }
        logger.info("maxServerSeq from: {}, to: {}, chatType: {}, serverSeq: {}, traceId: {}", from, to, serverSeq, chatType, traceId);
        return serverSeq;
    }

    @Override
    public ChatMessageDto lastMessage(String userId, String chatId, Integer chatType, String traceId) {
        ChatMessageDto chatMessageDto = null;
        if (ChatType.C2C.match(chatType)) {
            ImMessage imMessage = this.imMessageService.lastMessage(userId, chatId, traceId);
            if (imMessage != null) {
                chatMessageDto = MessageConvert.convert2ChatMessage(imMessage);
            }
        } else if (ChatType.C2G.match(chatType)) {
            ImMessageGroup imMessageGroup = this.imMessageGroupService.lastMessage(chatId, traceId);
            if (imMessageGroup != null) {
                chatMessageDto = MessageConvert.convert2ChatMessage(imMessageGroup);
            }
        } else {
            logger.error("lastMessage, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        return chatMessageDto;
    }
}