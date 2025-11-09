package com.github.xuning888.helloim.store.service.impl;

import com.github.xuning888.helloim.contract.api.service.ChatStoreService;
import com.github.xuning888.helloim.contract.entity.ImChat;
import com.github.xuning888.helloim.store.config.ShardingContextHolder;
import com.github.xuning888.helloim.store.mapper.ImChatMapper;
import com.github.xuning888.helloim.store.utils.ShardingUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 15:58
 */
@DubboService
public class ChatStoreServiceImpl implements ChatStoreService {

    private static final Logger logger = LoggerFactory.getLogger(ChatStoreServiceImpl.class);

    @Resource
    private ImChatMapper imChatMapper;

    @Override
    public int createOrUpdate(ImChat imChat, String traceId) {
        logger.info("createOrActivate, imChat: {}, traceId: {}", imChat, traceId);
        Long userId = imChat.getUserId(), chatId = imChat.getChatId();
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(userId));
        try {
            ImChat chat = imChatMapper.getChat(userId, chatId);
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
    public List<ImChat> getAllChat(String userId, String traceId) {
        logger.info("getAllChat, userId: {}, traceId: {}", userId, traceId);
        Long userIdInt64 = Long.valueOf(userId);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(userIdInt64));
        try {
            List<ImChat> imChats = imChatMapper.selectAllChat(userIdInt64);
            if (CollectionUtils.isEmpty(imChats)) {
                return Collections.emptyList();
            }
            return imChats;
        } finally {
            ShardingContextHolder.clear();
        }
    }

    @Override
    public int batchCreateOrUpdate(List<ImChat> imChats, String traceId) {
        int raw = 0;
        for (ImChat imChat : imChats) {
            raw += this.createOrUpdate(imChat, traceId);
        }
        return raw;
    }
}
