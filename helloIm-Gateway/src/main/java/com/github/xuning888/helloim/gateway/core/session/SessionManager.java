package com.github.xuning888.helloim.gateway.core.session;

import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.gateway.core.conn.event.ConnStateEvent;

/**
 * @author xuning
 * @date 2025/8/10 16:07
 */
public interface SessionManager {

    /**
     * auth成功后，保存session
     */
    void putSession(Session session, String traceId);

    /**
     *  关闭session, 传递关闭原因, 长连接需要关闭时触发
     * @param connStateEvent state
     */
    void changeState(ConnStateEvent connStateEvent);

    /**
     * 通过sessionId 获取session
     */
    Session getSession(String sessionId, String traceId);

    /**
     * 登出并传递事件
     */
    void logout(String sessionId, String traceId);
}
