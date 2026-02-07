package com.github.xuning888.helloim.store.mapper;

import com.github.xuning888.helloim.contract.entity.ImChatDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImChatMapper {

    int insertSelective(ImChatDo record);

    int updateByPrimaryKeySelective(ImChatDo record);

    ImChatDo getChat(@Param("userId") Long userId, @Param("chatId") Long chatId);

    List<ImChatDo> selectAllChat(@Param("userId") Long userId);
}