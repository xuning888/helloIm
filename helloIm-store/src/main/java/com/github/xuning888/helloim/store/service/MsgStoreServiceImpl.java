package com.github.xuning888.helloim.store.service;

import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.store.v1.*;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.convert.MessageConvert;
import com.github.xuning888.helloim.contract.entity.ImMessage;
import com.github.xuning888.helloim.contract.entity.ImMessageGroup;
import com.github.xuning888.helloim.store.repo.ImMessageRepo;
import com.github.xuning888.helloim.store.repo.ImMessageGroupRepo;
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
public class MsgStoreServiceImpl extends DubboMsgStoreServiceTriple.MsgStoreServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(MsgStoreServiceImpl.class);

    @Resource
    private ImMessageRepo imMessageRepo;

    @Resource
    private ImMessageGroupRepo imMessageGroupService;

    @Override
    public SaveMessageResponse saveMessage(SaveMessageRequest request) {
        ChatMessage chatMessage = request.getChatMessage();
        String traceId = request.getTraceId();
        int raw = 0;
        if (ChatType.C2C.match(chatMessage.getChatType())) {
            ImMessage imMessage = MessageConvert.convertImMessage(chatMessage);
            raw = this.imMessageRepo.saveMessage(imMessage, traceId);
        } else if (ChatType.C2G.match(chatMessage.getChatType())) {
            ImMessageGroup imMessageGroup = MessageConvert.convertImMessageGroup(chatMessage);
            raw = this.imMessageGroupService.saveMessage(imMessageGroup, traceId);
        }
        if (raw > 0) {
            logger.info("saveMessage success, msgId: {}, raw: {}, chatType: {}, traceId: {}",
                    chatMessage.getMsgId(), chatMessage.getChatType(), raw, traceId);
        } else {
            logger.warn("saveMessage failed, msgId: {}, chatType: {}, traceId: {}",
                    chatMessage.getMsgId(), chatMessage.getChatType(), traceId);
        }
        return SaveMessageResponse.newBuilder().setResult(raw).build();
    }

    @Override
    public MaxServerSeqResponse maxServerSeq(MaxServerSeqRequest request) {
        String from = request.getFrom();
        String to = request.getTo();
        int chatType = request.getChatType();
        String traceId = request.getTraceId();
        logger.info("maxServerSeq from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
        Long serverSeq = null;
        if (ChatType.C2C.match(chatType)) {
            serverSeq = this.imMessageRepo.maxServerSeq(from, to, traceId);
        } else if (ChatType.C2G.match(chatType)) {
            serverSeq = this.imMessageGroupService.maxServerSeq(to, traceId);
        } else {
            logger.error("maxServerSeq, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        if (serverSeq == null) {
            serverSeq = 0L;
        }
        return MaxServerSeqResponse.newBuilder().setMaxServerSeq(serverSeq).build();
    }

    @Override
    public LastMessageResponse lastMessage(LastMessageRequest request) {
        String userId = request.getUserId();
        String chatId = request.getChatId();
        int chatType = request.getChatType();
        String traceId = request.getTraceId();
        LastMessageResponse.Builder responseBuilder = LastMessageResponse.newBuilder();
        if (ChatType.C2C.match(chatType)) {
            ImMessage imMessage = this.imMessageRepo.lastMessage(userId, chatId, traceId);
            if (imMessage != null) {
                ChatMessage chatMessage = MessageConvert.convert2ChatMessage(imMessage);
                responseBuilder.setLastMessage(chatMessage);
            }
        } else if (ChatType.C2G.match(chatType)) {
            ImMessageGroup imMessageGroup = this.imMessageGroupService.lastMessage(chatId, traceId);
            if (imMessageGroup != null) {
                ChatMessage chatMessage = MessageConvert.convert2ChatMessage(imMessageGroup);
                responseBuilder.setLastMessage(chatMessage);
            }
        } else {
            logger.error("lastMessage, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        return responseBuilder.build();
    }

    @Override
    public SelectMessageByServerSeqsResponse selectMessageByServerSeqs(SelectMessageByServerSeqsRequest request) {
        SelectMessageByServerSeqsResponse.Builder responseBuilder = SelectMessageByServerSeqsResponse.newBuilder();
        String userId = request.getUserId();
        String chatId = request.getChatId();
        int chatType = request.getChatType();
        List<Long> serverSeqs = request.getServerSeqsList();
        String traceId = request.getTraceId();
        if (StringUtils.isEmpty(userId)) {
            logger.error("selectMessageWithServerSeqs userId is empty, traceId: {}", traceId);
            return responseBuilder.build();
        }
        if (StringUtils.isEmpty(chatId)) {
            logger.error("selectMessageWithServerSeqs chatId is empty, traceId: {}", traceId);
            return responseBuilder.build();
        }
        if (ChatType.C2C.match(chatType)) {
            List<ImMessage> imMessages = imMessageRepo.selectMessageByServerSeqs(userId, chatId, new HashSet<>(serverSeqs), traceId);
            List<ChatMessage> chatMessages = MessageConvert.convertImMessages2ChatMessages(imMessages);
            responseBuilder.addAllMessages(chatMessages);
        } else if (ChatType.C2G.match(chatType)) {
            List<ImMessageGroup> imMessageGroups = imMessageGroupService.selectMessageByServerSeqs(chatId, new HashSet<>(serverSeqs), traceId);
            List<ChatMessage> chatMessages = MessageConvert.convert2ChatMessages(imMessageGroups);
            responseBuilder.addAllMessages(chatMessages);
        } else {
            logger.error("selectMessageByServerSeqs, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        return responseBuilder.build();
    }

    @Override
    public GetRecentMessagesResponse getRecentMessages(GetRecentMessagesRequest request) {
        GetRecentMessagesResponse.Builder responseBuilder = GetRecentMessagesResponse.newBuilder();
        String traceId = request.getTraceId();
        if (StringUtils.isEmpty(request.getUserId())) {
            logger.error("getRecentMessages userId is empty, traceId: {}", traceId);
            return responseBuilder.build();
        }
        if (StringUtils.isEmpty(request.getChatId())) {
            logger.error("getRecentMessages chatId is empty, traceId: {}", traceId);
            return responseBuilder.build();
        }
        int chatType = request.getChatType();
        if (ChatType.C2C.match(chatType)) {
            List<ImMessage> imMessages = imMessageRepo.selectRecentMessages(request.getUserId(),
                    request.getChatId(), request.getLimit(), request.getTraceId());
            List<ChatMessage> chatMessages = MessageConvert.convertImMessages2ChatMessages(imMessages);
            responseBuilder.addAllMessages(chatMessages);
        } else if (ChatType.C2G.match(chatType)) {
            List<ImMessageGroup> imMessageGroups = imMessageGroupService.selectRecentMessages(request.getChatId(),
                    request.getLimit(), traceId);
            List<ChatMessage> chatMessages = MessageConvert.convert2ChatMessages(imMessageGroups);
            responseBuilder.addAllMessages(chatMessages);
        } else {
            logger.error("getRecentMessages, unknown chatType: {}, traceId: {}", chatType, traceId);
        }
        return responseBuilder.build();
    }
}