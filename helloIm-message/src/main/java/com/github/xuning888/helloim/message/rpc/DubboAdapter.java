package com.github.xuning888.helloim.message.rpc;

import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/9/21 17:33
 */
@Component
public class DubboAdapter {

    @DubboReference
    private MsgStoreService msgStoreService;

    public MsgStoreService msgStoreService() {
        return this.msgStoreService;
    }
}