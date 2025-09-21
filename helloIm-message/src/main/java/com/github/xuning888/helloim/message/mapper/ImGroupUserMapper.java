package com.github.xuning888.helloim.message.mapper;

import com.github.xuning888.helloim.contract.entity.ImGroupUser;

public interface ImGroupUserMapper {

    int insert(ImGroupUser record);

    int insertSelective(ImGroupUser record);


    int updateByPrimaryKeySelective(ImGroupUser record);

    int updateByPrimaryKey(ImGroupUser record);
}