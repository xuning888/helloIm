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

    // 离线消息的key的前缀
    public static String OFFLINE_MESSAGE_KEY_PREFIX =  "offline_message_";

    // 会话最后一条消息缓存key的前缀, 单聊
    public static String C2C_LAST_MESSAGE_KEY_PREFIX = "lastMessage_c2c_";

    //会话最后一条消息缓存key的前缀, 群聊
    public static String C2G_LAST_MESSAGE_KEY_PREFIX = "lastMessage_c2g_";
}
