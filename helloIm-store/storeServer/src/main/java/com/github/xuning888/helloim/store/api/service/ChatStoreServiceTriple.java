package com.github.xuning888.helloim.store.api.service;

import com.github.xuning888.helloim.contract.entity.ImChat;
import com.github.xuning888.helloim.store.api.DubboChatStoreServiceTriple;
import com.github.xuning888.helloim.store.api.Store;
import com.github.xuning888.helloim.store.config.ShardingContextHolder;
import com.github.xuning888.helloim.store.mapper.ImChatMapper;
import com.github.xuning888.helloim.store.utils.ShardingUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author xuning
 * @date 2025/11/2 19:17
 */
@DubboService
public class ChatStoreServiceTriple extends DubboChatStoreServiceTriple.ChatStoreServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(ChatStoreServiceTriple.class);

    @Resource
    private ImChatMapper imChatMapper;

    @Override
    public Store.CreateOrUpdateChatResponse createOrUpdate(Store.CreateOrUpdateChatRequest request) {
        Store.ImChat imChat = request.getImChat();
        String traceId = request.getTraceId();
        logger.info("createOrUpdate, imChat: {}, traceId: {}", imChat, traceId);
        Long userId = imChat.getUserId(), chatId = imChat.getChatId();
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(userId));
        int raw = 0;
        try {
            ImChat chat = imChatMapper.getChat(userId, chatId);
            if (chat == null) {
                raw = imChatMapper.insertSelective(convertToChat(imChat));
            } else {
                raw = imChatMapper.updateByPrimaryKeySelective(convertToChat(imChat));
            }
        } finally {
            ShardingContextHolder.clear();
        }
        return Store.CreateOrUpdateChatResponse.newBuilder().setRaw(raw).build();
    }

    private ImChat convertToChat(Store.ImChat imChat) {
        ImChat target = new ImChat();
        target.setId(imChat.getId());
        target.setUserId(imChat.getUserId());
        target.setChatId(imChat.getChatId());
        target.setChatType(imChat.getChatType());
        target.setChatTop(imChat.getChatTop());
        target.setChatDel(imChat.getChatDel());
        target.setChatMute(imChat.getChatMute());
        return target;
    }
}
