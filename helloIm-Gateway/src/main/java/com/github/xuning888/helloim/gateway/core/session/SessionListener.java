package com.github.xuning888.helloim.gateway.core.session;

import com.github.xuning888.helloim.contract.contant.GateSessionEvent;

/**
 * session的观察者, 接口会被并发调用, 需要关注线程安全
 * @author xuning
 * @date 2025/8/10 15:53
 */
public interface SessionListener {


    void notifyEvent(Session session, GateSessionEvent sessionEvent);
}
