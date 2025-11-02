package com.github.xuning888.helloim.chat.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author xuning
 * @date 2025/10/7 16:03
 */
public class ServerSeqUtils {

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

    private static final int serverSeqTTL = (int) TimeUnit.DAYS.convert(7, TimeUnit.SECONDS);


    public static Long setServerSeq(RedisTemplate redisTemplate, String key, Long serverSeq) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(SERVER_SEQ_SCRIPT);
        script.setResultType(Long.class);
        int random = (int) (Math.random() * 3600);
        int ttl = serverSeqTTL + random;
        return (Long) redisTemplate.execute(script, Collections.singletonList(key), serverSeq, ttl);
    }
}
