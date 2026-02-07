package com.github.xuning888.helloim.store.repo;

import com.github.xuning888.helloim.contract.entity.ImMessage;

import java.util.List;
import java.util.Set;

/**
 * @author xuning
 * @date 2025/9/21 16:01
 */
public interface ImMessageRepo {

    int saveMessage(ImMessage imMessage, String traceId);

    Long maxServerSeq(String from, String to, String traceId);

    ImMessage lastMessage(String userId, String toUserId, String traceId);

    List<ImMessage> selectMessageByServerSeqs(String userId, String chatId, Set<Long> serverSeqs, String traceId);

    List<ImMessage> selectRecentMessages(String userId, String chatId, Integer limit, String traceId);
}
