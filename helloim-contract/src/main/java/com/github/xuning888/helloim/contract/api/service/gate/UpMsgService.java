package com.github.xuning888.helloim.contract.api.service.gate;

import com.github.xuning888.helloim.contract.api.request.AuthRequest;
import com.github.xuning888.helloim.contract.api.request.LogoutRequest;
import com.github.xuning888.helloim.contract.api.request.UpMessageReq;
import com.github.xuning888.helloim.contract.api.response.AuthResponse;
import com.github.xuning888.helloim.contract.api.response.LogoutResponse;

/**
 * @author xuning
 * @date 2025/8/2 16:08
 */
public interface UpMsgService {

    /**
     * 验证用户信息
     * @param authRequest req
     */
    AuthResponse auth(AuthRequest authRequest);

    /**
     * 发送上行消息
     * @param req req
     */
    void sendMessage(UpMessageReq req);

    /**
     * 传递用户长连接登出事件
     */
    LogoutResponse logout(LogoutRequest logoutRequest);
}
