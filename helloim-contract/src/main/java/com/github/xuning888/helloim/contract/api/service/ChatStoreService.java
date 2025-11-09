package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.entity.ImChat;

import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 15:58
 */
public interface ChatStoreService {

    int createOrUpdate(ImChat imChat, String traceId);

    /**
     * 从数据库拉去全量会话
     */
    List<ImChat> getAllChat(String userId, String traceId);

    int batchCreateOrUpdate(List<ImChat> imChats, String traceId);
}
