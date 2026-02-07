package com.github.xuning888.helloim.contract.convert;

import com.github.xuning888.helloim.api.protobuf.common.v1.ImChat;
import com.github.xuning888.helloim.contract.entity.ImChatDo;

import java.util.Date;

/**
 * @author xuning
 * @date 2025/11/9 21:11
 */
public class ChatConvert {


    public static ImChatDo convert2Do(ImChat imChat) {
        ImChatDo imChatDo = new ImChatDo();
        imChatDo.setChatId(imChat.getChatId());
        imChatDo.setUserId(imChat.getUserId());
        imChatDo.setChatType(imChat.getChatType());
        boolean chatTop = imChat.getChatTop();
        imChatDo.setChatTop(chatTop ? 1 : 0);
        boolean chatDel = imChat.getChatDel();
        imChatDo.setChatDel(chatDel ? 1 : 0);
        long updateTimestamp = imChat.getUpdateTimestamp();
        if (updateTimestamp > 0) {
            imChatDo.setUpdateTimestamp(new Date(updateTimestamp));
        }
        long delTimestamp = imChat.getDelTimestamp();
        if (delTimestamp > 0) {
            imChatDo.setDelTimestamp(new Date(delTimestamp));
        }
        boolean chatMute = imChat.getChatMute();
        imChatDo.setChatMute(chatMute ? 1 : 0);
        return imChatDo;
    }

    public static ImChat convertImChat(ImChatDo imChatDo) {
        ImChat.Builder builder = ImChat.newBuilder();
        builder.setUserId(imChatDo.getUserId());
        builder.setChatId(imChatDo.getChatId());
        builder.setChatType(imChatDo.getChatType());
        Integer chatTop = imChatDo.getChatTop();
        if (chatTop == null) {
            builder.setChatTop(false);
        } else {
            builder.setChatTop(chatTop == 1);
        }
        Integer chatMute = imChatDo.getChatMute();
        if (chatMute == null) {
            builder.setChatMute(false);
        } else {
            builder.setChatMute(chatMute == 1);
        }
        Integer chatDel = imChatDo.getChatDel();
        if (chatDel == null) {
            builder.setChatDel(false);
        } else {
            builder.setChatDel(chatDel == 1);
        }
        Date updateTimestamp = imChatDo.getUpdateTimestamp();
        if (updateTimestamp == null) {
            builder.setUpdateTimestamp(System.currentTimeMillis());
        } else {
            builder.setUpdateTimestamp(updateTimestamp.getTime());
        }
        Date delTimestamp = imChatDo.getDelTimestamp();
        if (delTimestamp == null) {
            builder.setDelTimestamp(System.currentTimeMillis());
        } else {
            builder.setDelTimestamp(delTimestamp.getTime());
        }
        builder.setLastReadMsgId(0L);
        return builder.build();
    }
}
