package com.github.xuning888.helloim.store.service.impl;

import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.convert.MessageConvert;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.entity.ImMessage;
import com.github.xuning888.helloim.contract.entity.ImMessageGroup;
import com.github.xuning888.helloim.store.service.ImMessageGroupService;
import com.github.xuning888.helloim.store.service.ImMessageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

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

    @Override
    public List<ChatMessageDto> selectMessageByServerSeqs(String userId, String chatId, Integer chatType, Set<Long> serverSeqs, String traceId) {
        if (StringUtils.isEmpty(userId)) {
            logger.error("selectMessageWithServerSeqs userId is empty, traceId: {}", traceId);
            return Collections.emptyList();
        }
        if (StringUtils.isEmpty(chatId)) {
            logger.error("selectMessageWithServerSeqs chatId is empty, traceId: {}", traceId);
            return Collections.emptyList();
        }
        if (Objects.isNull(chatType)) {
            logger.error("selectMessageWithServerSeqs chatType is empty, traceId: {}", traceId);
            return Collections.emptyList();
        }
        if (ChatType.C2C.match(chatType)) {
            List<ImMessage> imMessages = imMessageService.selectMessageByServerSeqs(userId, chatId, serverSeqs, traceId);
            return MessageConvert.convertImMessages2ChatMessages(imMessages);
        } else if (ChatType.C2G.match(chatType)) {
            List<ImMessageGroup> imMessageGroups = imMessageGroupService.selectMessageByServerSeqs(chatId, serverSeqs, traceId);
            return MessageConvert.convert2ChatMessages(imMessageGroups);
        } else {
            logger.error("selectMessageByServerSeqs, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ChatMessageDto> getRecentMessages(String userId, String chatId, Integer chatType, Integer limit, String traceId) {
        if (StringUtils.isEmpty(userId)) {
            logger.error("getRecentMessages userId is empty, traceId: {}", traceId);
            return Collections.emptyList();
        }
        if (StringUtils.isEmpty(chatId)) {
            logger.error("getRecentMessages chatId is empty, traceId: {}", traceId);
            return Collections.emptyList();
        }
        if (Objects.isNull(chatType)) {
            logger.error("getRecentMessages chatType is empty, traceId: {}", traceId);
            return Collections.emptyList();
        }
        if (ChatType.C2C.match(chatType)) {
            List<ImMessage> imMessages = imMessageService.selectRecentMessages(userId, chatId, limit, traceId);
            return MessageConvert.convertImMessages2ChatMessages(imMessages);
        } else if (ChatType.C2G.match(chatType)) {
            List<ImMessageGroup> imMessageGroups = imMessageGroupService.selectRecentMessages(chatId, limit, traceId);
            return MessageConvert.convert2ChatMessages(imMessageGroups);
        } else {
            logger.error("getRecentMessages, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        return Collections.emptyList();
    }

}