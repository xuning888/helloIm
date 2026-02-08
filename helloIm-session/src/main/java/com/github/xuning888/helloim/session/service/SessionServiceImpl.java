package com.github.xuning888.helloim.session.service;


import com.github.xuning888.helloim.api.protobuf.common.v1.Endpoint;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateType;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateUser;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImSession;
import com.github.xuning888.helloim.api.protobuf.session.v1.*;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xuning
 * @date 2026/2/8 17:24
 */
@DubboService
public class SessionServiceImpl extends DubboSessionServiceTriple.SessionServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_PREFIX_";

    private static final Long sessionTTL = 24 * 3600L;

    private final RedisTemplate redisTemplate;

    public SessionServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public SaveSessionResponse saveSession(SaveSessionRequest request) {
        ImSession imSession = request.getImSession();
        GateUser gateUser = imSession.getGateUser();
        Endpoint endpoint = imSession.getEndpoint();
        GateType gateType = endpoint.getGateType();
        String key = sessionKey(gateUser, gateType);
        String jsonStr = ProtobufUtils.toJson(imSession);
        this.redisTemplate.opsForValue().set(key, jsonStr, sessionTTL, TimeUnit.SECONDS);
        return SaveSessionResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public MultiGetImSessionResponse multiGetSession(MultiGetImSessionRequest request) {
        List<GateUser> users = request.getUsersList();
        GateType gateType = request.getGateType();
        List<String> keys = new ArrayList<>(users.size());
        for (GateUser user : users) {
            String key = sessionKey(user, gateType);
            keys.add(key);
        }
        List list = this.redisTemplate.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(list)) {
            return MultiGetImSessionResponse.newBuilder().build();
        }
        MultiGetImSessionResponse.Builder builder = MultiGetImSessionResponse.newBuilder();
        for (Object value : list) {
            if (value == null) {
                continue;
            }
            String jsonStr = String.valueOf(value);
            ImSession imSession = ProtobufUtils.fromJson(jsonStr, ImSession.newBuilder());
            builder.addImSessions(imSession);
        }
        return builder.build();
    }

    @Override
    public RemoveImSessionResponse removeImSession(RemoveImSessionRequest request) {
        ImSession imSession = request.getImSession();
        GateUser gateUser = imSession.getGateUser();
        Endpoint endpoint = imSession.getEndpoint();
        String traceId = request.getTraceId();
        String key = sessionKey(gateUser, endpoint.getGateType());
        // auth 和 logout 有概率出现并发调用
        // case: 长连接断开后, 立刻auth, auth先执行把老的session覆盖了，logout在线程池中排队，这时不能删除
        // TODO 用lua
        ImSession oldSession = this.getSession(gateUser, endpoint.getGateType());
        if (oldSession != null) {
            // 校验一下，只有相等时才删除
            if (Objects.equals(oldSession, imSession)) {
                this.redisTemplate.delete(key);
                logger.info("removeSession success imSession: {}, traceId: {}", ProtobufUtils.toJson(imSession), traceId);
            }
        }
        return RemoveImSessionResponse.newBuilder().setSuccess(true).build();
    }

    private ImSession getSession(GateUser gateUser, GateType gateType) {
        String key = sessionKey(gateUser, gateType);
        Object value = this.redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        String jsonStr = String.valueOf(value);
        return ProtobufUtils.fromJson(jsonStr, ImSession.newBuilder());
    }

    private String sessionKey(GateUser gateUser, GateType gateType) {
        String str = String.format("%d_%d", gateUser.getUid(), gateUser.getUserType());
        return SESSION_KEY_PREFIX + str + "_" + gateType;
    }
}
