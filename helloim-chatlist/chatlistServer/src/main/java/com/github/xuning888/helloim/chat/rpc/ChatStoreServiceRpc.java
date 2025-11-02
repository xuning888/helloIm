package com.github.xuning888.helloim.chat.rpc;

import com.github.xuning888.helloim.store.api.ChatStoreService;
import com.github.xuning888.helloim.store.api.Store;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/11/2 19:23
 */
@Component
public class ChatStoreServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(ChatStoreServiceRpc.class);

    @DubboReference
    private ChatStoreService chatStoreService;

    public Store.CreateOrUpdateChatResponse createOrUpdate(Store.CreateOrUpdateChatRequest request) {
        logger.info("createOrUpdate, request: {}", request);
        Store.CreateOrUpdateChatResponse response = chatStoreService.createOrUpdate(request);
        logger.info("createOrUpdate, response: {}", response);
        return response;
    }
}
