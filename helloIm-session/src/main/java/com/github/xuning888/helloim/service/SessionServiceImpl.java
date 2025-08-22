package com.github.xuning888.helloim.service;

import com.github.xuning888.helloim.contract.api.service.SessionService;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateType;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.meta.ImSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2025/8/21 1:51
 */
@DubboService
public class SessionServiceImpl implements SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_PREFIX_";

    private final RedisTemplate redisTemplate;

    public SessionServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveSession(ImSession imSession, String traceId) {
        GateUser gateUser = imSession.getGateUser();
        Endpoint endpoint = imSession.getEndpoint();
        GateType gateType = endpoint.getGateType();
        String key = sessionKey(gateUser, gateType);
        logger.info("saveSession, key: {}", key);
        this.redisTemplate.opsForValue().set(key, imSession);
    }

    @Override
    public ImSession getSession(GateUser user, GateType gateType, String traceId) {
        String key = sessionKey(user, gateType);
        logger.info("getSession, key: {}", key);
        Object value = redisTemplate.opsForValue().get(key);
        return (ImSession) value;
    }

    @Override
    public List<ImSession> batchGetSession(List<GateUser> users, GateType gateType, String traceId) {
        List<ImSession> sessions = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        for (GateUser user : users) {
            String key = sessionKey(user, gateType);
            keys.add(key);
        }
        logger.info("batchGetSession keys: {}, traceId: {}", keys, traceId);
        List list = redisTemplate.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        logger.info("batchGetSession, keys: {}, result size: {}, traceId: {}", keys, list.size(), traceId);
        for (Object value : list) {
            sessions.add((ImSession) value);
        }
        return sessions;
    }

    @Override
    public void removeSession(ImSession imSession, String traceId) {
        GateUser gateUser = imSession.getGateUser();
        Endpoint endpoint = imSession.getEndpoint();
        String key = sessionKey(gateUser, endpoint.getGateType());
        logger.info("removeSession, key: {}, traceId: {}", key, traceId);
        this.redisTemplate.delete(key);
    }

    private String sessionKey(GateUser gateUser, GateType gateType) {
        return SESSION_KEY_PREFIX + gateUser + "_" + gateType;
    }
}