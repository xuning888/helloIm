package com.github.xuning888.helloim.store.service;

import com.github.xuning888.helloim.contract.entity.ImMessage;

/**
 * @author xuning
 * @date 2025/9/21 16:01
 */
public interface ImMessageService {

    int saveMessage(ImMessage imMessage, String traceId);

    Long maxServerSeq(String from, String to, String traceId);

    ImMessage lastMessage(String userId, String toUserId, String traceId);
}
