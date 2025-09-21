package com.github.xuning888.helloim.user.mapper;

import com.github.xuning888.helloim.contract.entity.ImUser;

public interface ImUserMapper {
    int insert(ImUser record);

    int insertSelective(ImUser record);

    ImUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(ImUser record);

    int updateByPrimaryKey(ImUser record);
}