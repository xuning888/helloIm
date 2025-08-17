package com.github.xuning888.helloim.gateway.adapter;

import com.github.xuning888.helloim.contract.api.service.gate.UpMsgService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/8/10 23:48
 */
@Component
public class UpMsgServiceAdapter {

    @DubboReference(
         methods = {
                 @Method(name = "sendMessage", timeout = 10000)
         }
    )
    private UpMsgService upMsgService;

    public UpMsgService upMsgService() {
        return this.upMsgService;
    }
}