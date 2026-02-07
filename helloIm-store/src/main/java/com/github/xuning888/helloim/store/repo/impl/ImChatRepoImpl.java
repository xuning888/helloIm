package com.github.xuning888.helloim.store.repo.impl;


import com.github.xuning888.helloim.contract.entity.ImChatDo;
import com.github.xuning888.helloim.store.config.ShardingContextHolder;
import com.github.xuning888.helloim.store.mapper.ImChatMapper;
import com.github.xuning888.helloim.store.repo.ImChatRepo;
import com.github.xuning888.helloim.store.utils.ShardingUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2026/2/7 17:35
 */
@Repository
public class ImChatRepoImpl implements ImChatRepo {
    private static final Logger logger = LoggerFactory.getLogger(ImChatRepoImpl.class);

    @Resource
    private ImChatMapper imChatMapper;

    @Override
    public int createOrUpdate(ImChatDo imChat, String traceId) {
        logger.info("createOrActivate, imChat: {}, traceId: {}", imChat, traceId);
        Long userId = imChat.getUserId(), chatId = imChat.getChatId();
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(userId));
        try {
            ImChatDo chat = imChatMapper.getChat(userId, chatId);
            if (chat == null) {
                return imChatMapper.insertSelective(imChat);
            } else {
                return imChatMapper.updateByPrimaryKeySelective(imChat);
            }
        } finally {
            ShardingContextHolder.clear();
        }
    }

    @Override
    public List<ImChatDo> getAllChat(String userId, String traceId) {
        logger.info("getAllChat, userId: {}, traceId: {}", userId, traceId);
        Long userIdInt64 = Long.valueOf(userId);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(userIdInt64));
        try {
            List<ImChatDo> imChats = imChatMapper.selectAllChat(userIdInt64);
            if (CollectionUtils.isEmpty(imChats)) {
                return Collections.emptyList();
            }
            return imChats;
        } finally {
            ShardingContextHolder.clear();
        }
    }

    @Override
    public int batchCreateOrUpdate(List<ImChatDo> imChats, String traceId) {
        int raw = 0;
        for (ImChatDo imChat : imChats) {
            raw += this.createOrUpdate(imChat, traceId);
        }
        return raw;
    }
}
