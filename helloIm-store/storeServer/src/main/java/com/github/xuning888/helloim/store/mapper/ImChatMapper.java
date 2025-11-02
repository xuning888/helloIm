package com.github.xuning888.helloim.store.mapper;

import com.github.xuning888.helloim.contract.entity.ImChat;
import org.apache.ibatis.annotations.Param;

public interface ImChatMapper {
    int insert(ImChat record);

    int insertSelective(ImChat record);

    int updateByPrimaryKeySelective(ImChat record);

    int updateByPrimaryKey(ImChat record);

    ImChat getChat(@Param("userId") Long userId, @Param("chatId") Long chatId);
}