package com.github.xuning888.helloim.contract.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

/**
 * @author xuning
 * @date 2025/8/2 22:19
 */
public class IdGenerator implements CommandLineRunner {

    private static final String SCRIPT =
            "if redis.call('exists', KEYS[1]) == 1 then\n" +
                    "    local value = redis.call('get', KEYS[1])\n" +
                    "    if tonumber(value) >= 1023 then\n" +
                    "        redis.call('set', KEYS[1], '1')\n" +
                    "        return 1\n" +
                    "    else\n" +
                    "        return redis.call('incr', KEYS[1])\n" +
                    "    end\n" +
                    "else\n" +
                    "    return redis.call('incr', KEYS[1])\n" +
                    "end\n";


    private Snowflake snowflake;

    private final RedisTemplate<String, Object> redisTemplate;
    private final String redisKey;

    public IdGenerator(RedisTemplate<String, Object> redisTemplate, String redisKey) {
        this.redisTemplate = redisTemplate;
        this.redisKey = redisKey;
    }

    @Override
    public void run(String... args) throws Exception {
        int workerId = getWorkerId();
        this.snowflake = new Snowflake(workerId);
    }


    private int getWorkerId() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(SCRIPT);
        script.setResultType(Long.class);
        Long value = this.redisTemplate.execute(script, Collections.singletonList(redisKey));
        return value.intValue();
    }

    public Long nextId() {
        return this.snowflake.nextId();
    }
}