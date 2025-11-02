package com.github.xuning888.helloim.message.rpc;

import com.alibaba.fastjson2.JSON;
import com.github.xuning888.helloim.store.api.MsgStoreService;
import com.github.xuning888.helloim.store.api.Store;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/11/2 21:05
 */
@Component
public class MsgStoreServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(MsgStoreServiceRpc.class);

    @DubboReference
    private MsgStoreService msgStoreService;

    public Store.SaveMessageResponse saveMessage(Store.SaveMessageRequest request) {
        logger.info("saveMessage request: {}", JSON.toJSONString(request));
        Store.SaveMessageResponse response = msgStoreService.saveMessage(request);
        logger.info("saveMessage response: {}", JSON.toJSONString(response));
        return response;
    }
}
