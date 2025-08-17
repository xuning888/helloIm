package com.github.xuning888.helloim.chat.service;

import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xuning
 * @date 2025/8/2 19:37
 */
@DubboService
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    private static final String SERVER_SEQ_SCRIPT =
            "if redis.call('exists', KEYS[1]) == 1 then\n" +
                    "    local value = redis.call('get', KEYS[1])\n" +
                    "    if tonumber(ARGV[1]) > tonumber(value) then\n" +
                    "        redis.call('set', KEYS[1], ARGV[1])\n" +
                    "        redis.call('expire', KEYS[1], ARGV[2])\n" +
                    "        return redis.call('incr', KEYS[1])\n" +
                    "    else\n" +
                    "        return redis.call('incr', KEYS[1])\n" +
                    "    end\n" +
                    "else\n" +
                    "    redis.call('setnx', KEYS[1], ARGV[1])\n" +
                    "    redis.call('expire', KEYS[1], ARGV[2])\n" +
                    "    return redis.call('incr', KEYS[1])\n" +
                    "end";

    private final int serverSeqTTL = (int) TimeUnit.DAYS.convert(7, TimeUnit.SECONDS);


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long serverSeq(String from, String to, ChatType chatType, String traceId) {
        String key = serverSeqKey(from, to, chatType);
        if (StringUtils.isBlank(key)) {
            logger.error("serverSeq serverSeqKey is null, from: {}, to: {}, chatType: {} traceId: {}",
                    from, to, chatType, traceId);
            return ERROR_SERVER_SEQ;
        }
        Long serverSeq  = null;
        Object value = this.redisTemplate.opsForValue().get(key);
        if (value == null) {
            try {
                serverSeq = serverSeqFromDB(from, to, chatType, traceId);
                if (serverSeq == null || serverSeq.equals(ERROR_SERVER_SEQ)) {
                    logger.error("serverSeq DB query failed, traceId: {}", traceId);
                    return ERROR_SERVER_SEQ;
                }
                return setServerSeq(key, serverSeq);
            } catch (Exception ex) {
                logger.error("serverSeq 重建缓存失败, from: {}, to: {}, chatType: {}, traceId: {}",
                        from, to, serverSeq, traceId, ex);
                return ERROR_SERVER_SEQ;
            }
        }
        try {
            serverSeq = this.redisTemplate.opsForValue().increment(key);
        } catch (Exception ex) {
            logger.error("serverSeq incr失败, from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId, ex);
            return ERROR_SERVER_SEQ;
        }
        if (serverSeq == null) {
            logger.error("serverSeq incr失败2, from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
            return ERROR_SERVER_SEQ;
        }
        return serverSeq;
    }

    private Long setServerSeq(String key, Long serverSeq) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(SERVER_SEQ_SCRIPT);
        script.setResultType(Long.class);
        int random = (int) (Math.random() * 3600);
        int ttl = serverSeqTTL + random;
        return this.redisTemplate.execute(script, Collections.singletonList(key), serverSeq, ttl);
    }


    private String serverSeqKey(String from, String to, ChatType chatType) {
        if (ChatType.C2C.equals(chatType)) {
            List<String> list = Arrays.asList(from, to);
            list.sort(String::compareTo);
            return "serverSeq_" + String.join("_", list) + chatType;
        } else if (ChatType.C2G.equals(chatType)) {
            return "serverSeq_" + to + "_" + chatType;
        } else {
            // 未知会话类型
            logger.error("serverSeqKey unKnown chatType: {}", chatType);
            return null;
        }
    }

    private Long serverSeqFromDB(String from, String to, ChatType chatType, String traceId) {
        if (ChatType.C2C.equals(chatType)) {
            // 查询单聊数据库
            return 1L;
        } else if (ChatType.C2G.equals(chatType)) {
            // 查询群聊数据库
            return 1L;
        } else {
            return ERROR_SERVER_SEQ;
        }
    }
}