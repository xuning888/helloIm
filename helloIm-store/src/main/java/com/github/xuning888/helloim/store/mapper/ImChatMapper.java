package com.github.xuning888.helloim.store.mapper;

import com.github.xuning888.helloim.contract.entity.ImChat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImChatMapper {

    int insertSelective(ImChat record);

    int updateByPrimaryKeySelective(ImChat record);

    ImChat getChat(@Param("userId") Long userId, @Param("chatId") Long chatId);

    List<ImChat> selectAllChat(@Param("userId") Long userId);
}