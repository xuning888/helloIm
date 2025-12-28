package com.github.xuning888.helloim.chat.utils;


import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

/**
 * @author xuning
 * @date 2025/12/28 15:03
 */
public class LastMessageUtils {

    private static final Logger  logger = LoggerFactory.getLogger(LastMessageUtils.class);

    private static final String updateC2CLastMsgScript =
                    "local exists = redis.call('exists', KEYS[1])\n" +
                    "if exists == 0 then\n" +
                    "    redis.call('set', KEYS[1], ARGV[1])\n" +
                    "    return 1\n" +
                    "else\n" +
                    "    local value = redis.call('get', KEYS[1])\n" +
                    "    local data = cjson.decode(value)\n" +
                    "    if tonumber(data.serverSeq) < tonumber(ARGV[2]) then\n" +
                    "        redis.call('set', KEYS[1], ARGV[1])\n" +
                    "        return 1\n" +
                    "    else\n" +
                    "        return 0\n" +
                    "    end\n" +
                    "end";

    private static final DefaultRedisScript<Long> updateC2cLastMsgScript;

    static {
        updateC2cLastMsgScript = new DefaultRedisScript<>();
        updateC2cLastMsgScript.setScriptText(updateC2CLastMsgScript);
        updateC2cLastMsgScript.setResultType(Long.class);
    }

    public static boolean updateC2CLastMessage(RedisTemplate<String, Object> redisTemplate,
                                               String userId, String toUserId, ChatMessageDto chatMessageDto,
                                               String traceId) {
        String key = RedisKeyUtils.c2cLastMessageKey(userId, toUserId);
        Long serverSeq = chatMessageDto.getServerSeq();
        Long msgId = chatMessageDto.getMsgId();
        logger.info("updateC2cLastMessage key: {}, msgId: {}, serverSeq: {}, traceId: {}", key, msgId, serverSeq, traceId);
        Long res = redisTemplate.execute(updateC2cLastMsgScript, Collections.singletonList(key), chatMessageDto, serverSeq);
        boolean updated = res > 0;
        if (!updated) {
            logger.info("updateC2cLastMessage, 最后一条消息更新失败, traceId: {}", traceId);
        }
        return updated;
    }
}
