package com.github.xuning888.helloim.contract.contant;

/**
 * @author xuning
 * @date 2025/10/14 22:18
 */
public class RedisConstant {

    // 服务端seq的key的前缀
    public static String SERVER_SEQ_KEY_PREFIX = "serverSeq_";

    // 会话索引的key的前缀
    public static String CHAT_INDEX_KEY_PREFIX = "chatIndex_";

    // 会话最后一条消息key的前缀
    public static String CHAT_LAST_MESSAGE_PREFIX = "chatLastMessage_";

    public static String GROUP_CHAT_LAST_MESSAGE_PREFIX = "group_chatLastMessage_";

    // 离线消息的key的前缀
    public static String OFFLINE_MESSAGE_KEY_PREFIX =  "offline_message_";
}
