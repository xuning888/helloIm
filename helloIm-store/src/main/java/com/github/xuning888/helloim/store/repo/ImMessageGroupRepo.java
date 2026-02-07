package com.github.xuning888.helloim.store.repo;

import com.github.xuning888.helloim.contract.entity.ImMessageGroup;

import java.util.List;
import java.util.Set;

/**
 * @author xuning
 * @date 2025/9/21 16:01
 */
public interface ImMessageGroupRepo {

    int saveMessage(ImMessageGroup imMessageGroup, String traceId);

    Long maxServerSeq(String groupId, String traceId);

    ImMessageGroup lastMessage(String groupId, String traceId);

    List<ImMessageGroup> selectMessageByServerSeqs(String chatId, Set<Long> serverSeqs, String traceId);

    List<ImMessageGroup> selectRecentMessages(String chatId, Integer limit, String traceId);
}
