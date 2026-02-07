package com.github.xuning888.helloim.api.convert;


import com.github.xuning888.helloim.api.dto.ImChatDto;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImChat;
import org.apache.dubbo.common.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2026/2/7 19:23
 */
public class ImChatConvert {

    public static ImChatDto convert2Dto(ImChat imChat) {
        ImChatDto imChatDto = new ImChatDto();
        imChatDto.setUserId(imChat.getUserId());
        imChatDto.setChatId(imChat.getChatId());
        imChatDto.setChatType(imChat.getChatType());
        imChatDto.setChatTop(imChat.getChatTop());
        imChatDto.setChatMute(imChat.getChatMute());
        imChatDto.setChatDel(imChat.getChatDel());
        imChatDto.setUpdateTimestamp(imChat.getUpdateTimestamp());
        imChatDto.setDelTimestamp(imChat.getDelTimestamp());
        imChatDto.setLastReadMsgId(imChat.getLastReadMsgId());
        imChatDto.setSubStatus(imChat.getSubStatus());
        imChatDto.setJoinGroupTimestamp(imChat.getJoinGroupTimestamp());
        return imChatDto;
    }

    public static List<ImChatDto> convert2Dto(List<ImChat> imChatList) {
        if (CollectionUtils.isEmpty(imChatList)) {
            return Collections.emptyList();
        }
        List<ImChatDto> imChatDtos = new ArrayList<>(imChatList.size());
        for (ImChat imChat : imChatList) {
            imChatDtos.add(convert2Dto(imChat));
        }
        return imChatDtos;
    }
}
