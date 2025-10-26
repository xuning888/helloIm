package com.github.xuning888.helloim.store.service.impl;

import com.github.xuning888.helloim.contract.api.service.ChatStoreService;
import com.github.xuning888.helloim.contract.entity.ImChat;
import com.github.xuning888.helloim.store.config.ShardingContextHolder;
import com.github.xuning888.helloim.store.mapper.ImChatMapper;
import com.github.xuning888.helloim.store.utils.ShardingUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

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
}
