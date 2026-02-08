package com.github.xuning888.helloim.gateway.rpc;


import com.github.xuning888.helloim.api.protobuf.gateway.v1.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2026/2/8 18:23
 */
@Component
public class UpMsgServiceRpc {

    @DubboReference(
            methods = {
                    @Method(name = "sendMessage", timeout = 10000)
            }
    )
    private UpMsgService msgService;

    public AuthResponse auth(AuthRequest authRequest) {
        return msgService.auth(authRequest);
    }

    public LogoutResponse logout(LogoutRequest logoutRequest) {
        return msgService.logout(logoutRequest);
    }

    public void sendMessage(UpMessageRequest request) {
        msgService.sendMessage(request);
    }
}
