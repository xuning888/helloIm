package com.github.xuning888.helloim.store.mapper;

import com.github.xuning888.helloim.contract.entity.ImChat;

public interface ImChatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImChat record);

    int insertSelective(ImChat record);

    ImChat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImChat record);

    int updateByPrimaryKey(ImChat record);
}