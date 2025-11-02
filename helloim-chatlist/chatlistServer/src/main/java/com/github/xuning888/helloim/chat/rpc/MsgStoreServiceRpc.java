package com.github.xuning888.helloim.chat.rpc;

import com.alibaba.fastjson2.JSON;
import com.github.xuning888.helloim.store.api.MsgStoreService;
import com.github.xuning888.helloim.store.api.Store;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/11/2 20:33
 */
@Component
public class MsgStoreServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(MsgStoreServiceRpc.class);

    @DubboReference
    private MsgStoreService msgStoreService;

    public Store.MaxServerSeqResponse maxServerSeq(Store.MaxServerSeqRequest request) {
        logger.info("maxServerSeq request: {}", JSON.toJSONString(request));
        Store.MaxServerSeqResponse response = msgStoreService.maxServerSeq(request);
        logger.info("maxServerSeq resposne: {}", JSON.toJSONString(response));
        return response;
    }

}
