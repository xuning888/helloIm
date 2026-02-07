package com.github.xuning888.helloim.store.service;

import com.github.xuning888.helloim.api.protobuf.common.v1.ImChat;
import com.github.xuning888.helloim.api.protobuf.store.v1.*;
import com.github.xuning888.helloim.contract.convert.ChatConvert;
import com.github.xuning888.helloim.contract.entity.ImChatDo;
import com.github.xuning888.helloim.store.repo.ImChatRepo;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 15:58
 */
@DubboService
public class ChatStoreServiceImpl extends DubboChatStoreServiceTriple.ChatStoreServiceImplBase {

    @Resource
    private ImChatRepo imChatRepo;

    @Override
    public CreateOrUpdateChatResponse createOrUpdate(CreateOrUpdateChatRequest request) {
        ImChat imChat = request.getImChat();
        String traceId = request.getTraceId();
        ImChatDo imChatDo = ChatConvert.convert2Do(imChat);
        int raw = imChatRepo.createOrUpdate(imChatDo, traceId);
        return CreateOrUpdateChatResponse.newBuilder().setResult(raw).setTraceId(traceId).build();
    }

    @Override
    public GetAllChatResponse getAllChat(GetAllChatRequest request) {
        String userId = request.getUserId();
        String traceId = request.getTraceId();
        List<ImChatDo> chats = this.imChatRepo.getAllChat(userId, traceId);
        GetAllChatResponse.Builder builder = GetAllChatResponse.newBuilder();
        for (ImChatDo chat : chats) {
            ImChat imChat = ChatConvert.convertImChat(chat);
            builder.addChats(imChat);
        }
        return builder.build();
    }

    @Override
    public BatchCreateOrUpdateResponse batchCreateOrUpdate(BatchCreateOrUpdateRequest request) {
        List<ImChat> chatsList = request.getChatsList();
        String traceId = request.getTraceId();
        List<ImChatDo> imChatDos = new ArrayList<>(chatsList.size());
        for (ImChat imChat : chatsList) {
            ImChatDo imChatDo = ChatConvert.convert2Do(imChat);
            imChatDos.add(imChatDo);
        }
        int raw = imChatRepo.batchCreateOrUpdate(imChatDos, traceId);
        return BatchCreateOrUpdateResponse.newBuilder().setResult(raw).build();
    }
}
