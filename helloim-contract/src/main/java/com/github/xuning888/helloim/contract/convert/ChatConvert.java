package com.github.xuning888.helloim.contract.convert;

import com.github.xuning888.helloim.api.dto.ImChatDto;
import com.github.xuning888.helloim.contract.entity.ImChat;

import java.util.Date;

/**
 * @author xuning
 * @date 2025/11/9 21:11
 */
public class ChatConvert {


    public static ImChat convertImChat(ImChatDto imChatDto) {
        ImChat imChat = new ImChat();
        imChat.setChatId(imChatDto.getChatId());
        imChat.setUserId(imChatDto.getUserId());
        imChat.setChatType(imChatDto.getChatType());
        if (imChatDto.getChatTop() != null) {
            imChat.setChatTop(imChatDto.getChatTop() ? 1 : 0);
        } else {
            imChat.setChatTop(0);
        }
        if (imChatDto.getChatDel() != null) {
            imChat.setChatDel(imChatDto.getChatDel() ? 1 : 0);
        } else {
            imChat.setChatDel(0);
        }
        Long updateTimestamp = imChatDto.getUpdateTimestamp();
        if (updateTimestamp != null) {
            imChat.setUpdateTimestamp(new Date(updateTimestamp));
        }
        Long delTimestamp = imChatDto.getDelTimestamp();
        if (delTimestamp != null) {
            imChat.setDelTimestamp(new Date(delTimestamp));
        }
        imChat.setChatMute(imChat.getChatMute());
        return imChat;
    }


    public static ImChatDto convertToimChatDto(ImChat imChat) {
        if (imChat == null) {
            return null;
        }
        ImChatDto imChatDto = new ImChatDto();
        imChatDto.setUserId(imChat.getUserId());
        imChatDto.setChatId(imChat.getChatId());
        imChatDto.setChatType(imChat.getChatType());
        Integer chatTop = imChat.getChatTop();
        if (chatTop == null) {
            imChatDto.setChatTop(false);
        } else {
            imChatDto.setChatTop(chatTop == 1);
        }
        Integer chatMute = imChat.getChatMute();
        if (chatMute == null) {
            imChatDto.setChatMute(false);
        } else {
            imChatDto.setChatMute(chatMute == 1);
        }
        Integer chatDel = imChat.getChatDel();
        if (chatDel == null) {
            imChatDto.setChatDel(false);
        } else {
            imChatDto.setChatDel(chatDel == 1);
        }
        Date updateTimestamp = imChat.getUpdateTimestamp();
        if (updateTimestamp == null) {
            imChatDto.setUpdateTimestamp(System.currentTimeMillis());
        } else {
            imChatDto.setUpdateTimestamp(updateTimestamp.getTime());
        }
        Date delTimestamp = imChat.getDelTimestamp();
        if (delTimestamp == null) {
            imChatDto.setDelTimestamp(System.currentTimeMillis());
        } else {
            imChatDto.setDelTimestamp(delTimestamp.getTime());
        }
        imChatDto.setLastReadMsgId(null);
        return imChatDto;
    }
}
