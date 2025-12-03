package com.github.xuning888.helloim.message.mapper;

import com.github.xuning888.helloim.contract.entity.ImGroup;


public interface ImGroupMapper {
    int deleteByPrimaryKey(Long groupId);

    int insert(ImGroup record);

    int insertSelective(ImGroup record);

    ImGroup selectByPrimaryKey(Long groupId);

    int updateByPrimaryKeySelective(ImGroup record);

    int updateByPrimaryKey(ImGroup record);
}