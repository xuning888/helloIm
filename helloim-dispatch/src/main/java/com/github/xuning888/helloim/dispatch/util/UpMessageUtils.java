package com.github.xuning888.helloim.dispatch.util;

import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Header;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import java.util.Collections;

/**
 * @author xuning
 * @date 2025/8/2 22:43
 */
public class UpMessageUtils {

    private static final String REPEAT_PREFIX = "dispatch_up_message_repeat_";

    private static final String SCRIPT =
            "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then\n" +
            "    redis.call('expire', KEYS[1], ARGV[2])\n" +
            "    return true\n" +
            "else\n" +
            "    return false\n" +
            "end";

    public static boolean isDuplicate(RedisTemplate<String, Object> redisTemplate, MsgContext msgContext) {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
        script.setScriptText(SCRIPT);
        script.setResultType(Boolean.class);
        String key = duplicateKey(msgContext);
        Boolean lock = redisTemplate.execute(script, Collections.singletonList(key), "", 60);
        if (lock) {
            // 加锁成功，说明消息没有重复
            return false;
        }
        return true;
    }

    public static void deleteDuplicate(RedisTemplate<String, Object> redisTemplate, MsgContext msgContext) {
        String key = duplicateKey(msgContext);
        redisTemplate.delete(key);
    }

    private static String duplicateKey(MsgContext msgContext) {
        Header header = msgContext.getFrame().getHeader();
        int seq = header.getSeq(), cmdId = header.getCmdId();
        String from = msgContext.getMsgFrom();
        return REPEAT_PREFIX + from + "_" + cmdId + "_" + seq;
    }

}