package com.github.xuning888.helloim.store.repo.impl;

import com.github.xuning888.helloim.contract.entity.ImMessage;
import com.github.xuning888.helloim.store.config.ShardingContextHolder;
import com.github.xuning888.helloim.store.mapper.ImMessageMapper;
import com.github.xuning888.helloim.store.repo.ImMessageRepo;
import com.github.xuning888.helloim.store.utils.ShardingUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xuning
 * @date 2025/9/21 16:15
 */
@Service("ImMessageRepoImpl")
public class ImMessageRepoImpl implements ImMessageRepo {

    @Resource
    private ImMessageMapper imMessageMapper;


    @Override
    public int saveMessage(ImMessage imMessage, String traceId) {
        Long msgFrom = imMessage.getMsgFrom();
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(msgFrom));
        try {
            return this.imMessageMapper.insertSelective(imMessage);
        } finally {
            ShardingContextHolder.clear();
        }
    }

    @Override
    public Long maxServerSeq(String from, String to, String traceId) {
        Long fromMaxServerSeq = selectMaxServerSeq(from, to, traceId);
        Long toMaxServerSeq = selectMaxServerSeq(to, from, traceId);
        if (fromMaxServerSeq == null && toMaxServerSeq == null) {
            return 0L;
        } else if (fromMaxServerSeq == null) {
            return toMaxServerSeq;
        } else if (toMaxServerSeq == null) {
            return fromMaxServerSeq;
        } else {
            return Math.max(fromMaxServerSeq, toMaxServerSeq);
        }
    }

    @Override
    public ImMessage lastMessage(String userId, String toUserId, String traceId) {
        ImMessage lastMessage1 = getLastMessage(userId, toUserId, traceId);
        ImMessage lastMessage2 = getLastMessage(toUserId, userId, traceId);
        if (lastMessage1 == null && lastMessage2 == null) {
            return null;
        } else if (lastMessage1 == null) {
            return lastMessage2;
        } else if (lastMessage2 == null) {
            return lastMessage1;
        } else {
            // 都能查询到
            if (lastMessage1.getServerSeq() > lastMessage2.getServerSeq()) {
                return lastMessage1;
            }
            return lastMessage2;
        }
    }

    @Override
    public List<ImMessage> selectMessageByServerSeqs(String userId, String chatId, Set<Long> serverSeqs, String traceId) {
        if (CollectionUtils.isEmpty(serverSeqs)) {
            return Collections.emptyList();
        }
        List<ImMessage> messages = new ArrayList<>(serverSeqs.size());
        List<ImMessage> imMessages = doSelectMessageByServerSeqs(userId, chatId, serverSeqs, traceId);
        Set<Long> findSeqs = new HashSet<>();
        for (ImMessage imMessage : imMessages) {
            findSeqs.add(imMessage.getServerSeq());
            messages.add(imMessage);
        }
        // 剩余的没有查找的seq
        Set<Long> remainingSeqs = new HashSet<>(serverSeqs);
        remainingSeqs.removeAll(findSeqs);
        List<ImMessage> imMessages1 = doSelectMessageByServerSeqs(chatId, userId, remainingSeqs, traceId);
        messages.addAll(imMessages1);
        messages.sort(Comparator.comparing(ImMessage::getServerSeq));
        return messages;
    }

    @Override
    public List<ImMessage> selectRecentMessages(String userId, String chatId, Integer limit, String traceId) {
        List<ImMessage> imMessages = doSelectRecentMessages(userId, chatId, limit, traceId);
        List<ImMessage> imMessages1 = doSelectRecentMessages(chatId, userId, limit, traceId);
        // 因为两边查出来的消息都是有序的,所以归并排序效果会好一些
        List<ImMessage> messages = mergeAndLimitMessages(imMessages, imMessages1, limit);
        Collections.reverse(messages);
        return messages;
    }

    private ImMessage getLastMessage(String userId, String msgTo, String traceId) {
        long userIdInt64 = Long.parseLong(userId);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(userIdInt64));
        try {
            long msgToInt64 = Long.parseLong(msgTo);
            return this.imMessageMapper.lastMessage(userIdInt64, msgToInt64);
        } finally {
            ShardingContextHolder.clear();
        }
    }

    private Long selectMaxServerSeq(String from, String to, String traceId) {
        long msgFrom = Long.parseLong(from), msgTo = Long.parseLong(to);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(msgFrom));
        try {
            return this.imMessageMapper.selectMaxServerSeq(msgFrom, msgTo);
        } finally {
            ShardingContextHolder.clear();
        }
    }

    private List<ImMessage> doSelectRecentMessages(String userId, String msgto, Integer limit, String traceId) {
        long userIdInt64 = Long.parseLong(userId);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(userIdInt64));
        try {
            long msgToInt64 = Long.parseLong(msgto);
            List<ImMessage> imMessages = this.imMessageMapper.selectRecentMessages(userIdInt64, msgToInt64, limit);
            if (CollectionUtils.isEmpty(imMessages)) {
                return Collections.emptyList();
            }
            return imMessages;
        } finally {
            ShardingContextHolder.clear();
        }
    }

    private List<ImMessage> doSelectMessageByServerSeqs(String userId, String msgto, Set<Long> serverSeqs, String traceId) {
        if (CollectionUtils.isEmpty(serverSeqs)) {
            return Collections.emptyList();
        }
        long userIdInt64 = Long.parseLong(userId);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(userIdInt64));
        try {
            long msgToInt64 = Long.parseLong(msgto);
            return this.imMessageMapper.selectMessageBySeqs(userIdInt64, msgToInt64, serverSeqs);
        } finally {
            ShardingContextHolder.clear();
        }
    }

    private List<ImMessage> mergeAndLimitMessages(List<ImMessage> list1, List<ImMessage> list2, int limit) {
        List<ImMessage> result = new ArrayList<>(limit);
        int i = 0, j = 0;
        int n = list1.size(), m = list2.size();
        while (result.size() < limit && i < n && j < m) {
            ImMessage msg1 = list1.get(i);
            ImMessage msg2 = list2.get(j);
            if (msg1.getServerSeq() > msg2.getServerSeq()) {
                result.add(msg1);
                i++;
            } else {
                result.add(msg2);
                j++;
            }
        }
        while (result.size() < limit && i < n) {
            result.add(list1.get(i++));
        }
        while (result.size() < limit && j < m) {
            result.add(list2.get(j++));
        }
        return result;
    }
}