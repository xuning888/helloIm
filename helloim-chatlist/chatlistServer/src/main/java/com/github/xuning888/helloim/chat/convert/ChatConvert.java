package com.github.xuning888.helloim.chat.convert;

import com.github.xuning888.helloim.chat.api.Chat;
import com.github.xuning888.helloim.store.api.Store;

/**
 * @author xuning
 * @date 2025/11/2 19:37
 */
public class ChatConvert {

    public static Chat.ImChat convert2ChatChat(Store.ImChat storeChat) {
        Chat.ImChat.Builder builder = Chat.ImChat.newBuilder();
        builder.setChatId(String.valueOf(storeChat.getChatId())); // 会话id
        builder.setChatType(storeChat.getChatType()); // 会话类型
        builder.setChatMute(storeChat.getChatMute() == 1); // 是否静默会话
        builder.setChatTop(storeChat.getChatTop() == 1); // 是否置顶会话
        builder.setChatDel(storeChat.getChatDel() == 1); // 是否逻辑删除会话
        builder.setDelTimestamp(storeChat.getDelTimestamp()); // 会话逻辑删除时间
        builder.setVersion(System.currentTimeMillis()); // 会话版本号
        builder.setLastMsgId(0L); // 会话最后一条消息id, 需要查询最后一条消息，然后批量更新
        builder.setLastMsgIdStr(""); // 会话最后一条消息id, 需要查询最后一条消息，然后批量更新
        builder.setUserId(String.valueOf(storeChat.getUserId())); // userId
        builder.setUpdateTimestamp(storeChat.getUpdateTimestamp()); // 会话更新时间
        return builder.build();
    }

    public static Store.ImChat convert2StoreChat(Chat.ImChat chatChat) {
        Store.ImChat.Builder builder = Store.ImChat.newBuilder();
        builder.setId(0L); // 主键ID
        builder.setUserId(Long.parseLong(chatChat.getUserId())); // userId
        builder.setChatId(Long.parseLong(chatChat.getChatId())); // 会话id
        builder.setChatType(chatChat.getChatType()); // 会话类型
        builder.setChatTop(chatChat.getChatType()); // 绘画是否置顶
        builder.setChatDel(chatChat.getChatDel() ? 1 : 0); // 是否逻辑删除
        builder.setChatMute(chatChat.getChatMute() ? 1 : 0);
        builder.setUpdateTimestamp(chatChat.getUpdateTimestamp()); // 会话更新的时间戳
        builder.setDelTimestamp(chatChat.getDelTimestamp()); // 会话逻辑删除的时间
        return builder.build();
    }
}
