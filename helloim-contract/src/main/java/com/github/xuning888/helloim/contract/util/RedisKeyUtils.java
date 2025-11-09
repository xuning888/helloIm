package com.github.xuning888.helloim.contract.util;

import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.RedisConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author xuning
 * @date 2025/10/14 22:16
 */
public class RedisKeyUtils {

    private static final Logger logger = LoggerFactory.getLogger(RedisKeyUtils.class);

    /**
     * 服务端seq的key
     */
    public static String serverSeqKey(String from, String to, ChatType chatType) {
        if (ChatType.C2C.equals(chatType)) {
            List<String> list = Arrays.asList(from, to);
            list.sort(String::compareTo);
            return RedisConstant.SERVER_SEQ_KEY_PREFIX + String.join("_", list) + "_" + chatType;
        } else if (ChatType.C2G.equals(chatType)) {
            return RedisConstant.SERVER_SEQ_KEY_PREFIX + to + "_" + chatType;
        } else {
            // 未知会话类型
            logger.error("serverSeqKey unKnown chatType: {}", chatType);
            return null;
        }
    }

    /**
     * 会话索引的key
     */
    public static String chatIndexKey(String userId) {
        return RedisConstant.CHAT_INDEX_KEY_PREFIX + userId;
    }

    public static String c2cLastMessageKey(String msgFrom) {
        return RedisConstant.C2C_LAST_MESSAGE_KEY_PREFIX + msgFrom;
    }

    public static String c2gLastMessageKey(Long groupId) {
        // 128个分片
        long index = groupId % 128;
        return RedisConstant.C2G_LAST_MESSAGE_KEY_PREFIX + index;
    }


    /**
     * 离线消息缓存的key
     */
    public static String offlineMsgKey(Long userId, Long toUserId, Long groupId, int chatType) {
        if (ChatType.C2C.match(chatType)) {
            return c2cOfflineMsgKey(userId, toUserId);
        } else if (ChatType.C2G.match(chatType)) {
            return c2gOfflineMsgKey(groupId);
        } else {
            logger.info("Unknown chatType, userId: {}, toUserId: {}, chatId: {}, chatType: {}", userId, toUserId, groupId, chatType);
            throw new IllegalArgumentException("Unknown chatType: " + chatType);
        }
    }

    /**
     * 构造单聊离线消息的key
     */
    public static String c2cOfflineMsgKey(Long msgFrom, Long msgTo) {
        long smallerId = Math.min(msgFrom, msgTo);
        long largerId = Math.max(msgFrom, msgTo);
        return RedisConstant.OFFLINE_MESSAGE_KEY_PREFIX + ChatType.C2C + "_" + smallerId + "_" + largerId;
    }

    /**
     * 构建群聊离线消息的key
     */
    public static String c2gOfflineMsgKey(Long groupId) {
        return RedisConstant.OFFLINE_MESSAGE_KEY_PREFIX + ChatType.C2G + "_" + groupId;
    }
}
