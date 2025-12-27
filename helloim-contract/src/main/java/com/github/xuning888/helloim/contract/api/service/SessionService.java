package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.meta.GateType;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.meta.ImSession;

import java.util.List;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/8/18 2:12
 */
public interface SessionService {

    /**
     * 保存session
     */
    void saveSession(ImSession imSession, String traceId);

    /**
     * 获取session
     */
    ImSession getSession(GateUser user, GateType gateType, String traceId);

    /**
     * 批量获取session
     */
    List<ImSession> batchGetSession(List<GateUser> users, GateType gateType, String traceId);

    /**
     * 批量获取session
     */
    Map<GateUser, ImSession> batchGetSessionMap(List<GateUser> users, GateType gateType, String traceId);

    /**
     * 移除session
     */
    void removeSession(ImSession imSession, String traceId);
}
