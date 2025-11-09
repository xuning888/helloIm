package com.github.xuning888.helloim.store.service;

import com.github.xuning888.helloim.contract.entity.ImMessageGroup;

/**
 * @author xuning
 * @date 2025/9/21 16:01
 */
public interface ImMessageGroupService {

    int saveMessage(ImMessageGroup imMessageGroup, String traceId);

    Long maxServerSeq(String groupId, String traceId);

    ImMessageGroup lastMessage(String groupId, String traceId);
}
