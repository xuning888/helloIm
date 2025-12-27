package com.github.xuning888.helloim.session.service;

import com.github.xuning888.helloim.contract.api.service.SessionService;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateType;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.meta.ImSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xuning
 * @date 2025/8/21 1:51
 */
@DubboService
public class SessionServiceImpl implements SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_PREFIX_";

    private static final Long sessionTTL = 24 * 3600L;

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
        this.redisTemplate.opsForValue().set(key, imSession, sessionTTL, TimeUnit.SECONDS);
    }

    @Override
    public ImSession getSession(GateUser user, GateType gateType, String traceId) {
        RpcServiceContext serviceContext = RpcContext.getServiceContext();
        String caller = serviceContext.getRemoteApplicationName();
        String key = sessionKey(user, gateType);
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            logger.info("getSession session is null, caller:{} key: {}, traceId: {}", caller, key, traceId);
            return null;
        }
        return (ImSession) value;
    }

    @Override
    public List<ImSession> batchGetSession(List<GateUser> users, GateType gateType, String traceId) {
        RpcServiceContext serviceContext = RpcContext.getServiceContext();
        String caller = serviceContext.getRemoteApplicationName();
        List<ImSession> sessions = new ArrayList<>();
        List<String> keys = new ArrayList<>(users.size());
        for (GateUser user : users) {
            String key = sessionKey(user, gateType);
            keys.add(key);
        }
        logger.info("batchGetSession caller: {}, keys: {}, traceId: {}", caller, keys, traceId);
        List list = redisTemplate.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        logger.info("batchGetSession, caller: {} keys: {}, result size: {}, traceId: {}", caller, keys, list.size(), traceId);
        for (Object value : list) {
            sessions.add((ImSession) value);
        }
        return sessions;
    }

    @Override
    public Map<GateUser, ImSession> batchGetSessionMap(List<GateUser> users, GateType gateType, String traceId) {
        List<ImSession> imSessions = batchGetSession(users, gateType, traceId);
        if (CollectionUtils.isEmpty(imSessions)) {
            return Collections.emptyMap();
        }
        Map<GateUser, ImSession> userImSessionMap = new HashMap<>();
        for (ImSession imSession : imSessions) {
            GateUser gateUser = imSession.getGateUser();
            userImSessionMap.put(gateUser, imSession);
        }
        return userImSessionMap;
    }

    @Override
    public void removeSession(ImSession imSession, String traceId) {
        GateUser gateUser = imSession.getGateUser();
        Endpoint endpoint = imSession.getEndpoint();
        String key = sessionKey(gateUser, endpoint.getGateType());
        // auth 和 logout 有概率出现并发调用
        // case: 长连接断开后, 立刻auth, auth先执行把老的session覆盖了，logout在线程池中排队，这时不能删除
        // TODO 用lua
        ImSession oldSession = this.getSession(gateUser, endpoint.getGateType(), traceId);
        if (oldSession != null) {
            // 校验一下，只有相等时才删除
            if (Objects.equals(oldSession, imSession)) {
                this.redisTemplate.delete(key);
                logger.info("removeSession success imSession: {}, traceId: {}", imSession, traceId);
            }
        }
    }

    private String sessionKey(GateUser gateUser, GateType gateType) {
        return SESSION_KEY_PREFIX + gateUser + "_" + gateType;
    }
}