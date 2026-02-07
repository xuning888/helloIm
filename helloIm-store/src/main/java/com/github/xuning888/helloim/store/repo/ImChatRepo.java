package com.github.xuning888.helloim.store.repo;


import com.github.xuning888.helloim.contract.entity.ImChatDo;

import java.util.List;

/**
 * @author xuning
 * @date 2026/2/7 17:34
 */
public interface ImChatRepo {

    int createOrUpdate(ImChatDo imChat, String traceId);

    /**
     * 从数据库拉去全量会话
     */
    List<ImChatDo> getAllChat(String userId, String traceId);

    int batchCreateOrUpdate(List<ImChatDo> imChats, String traceId);
}
