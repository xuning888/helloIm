package com.github.xuning888.helloim.message.mapper;

import com.github.xuning888.helloim.contract.entity.ImGroupUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface ImGroupUserMapper {
    int insertSelective(ImGroupUser record);

    int updateByPrimaryKeySelective(ImGroupUser record);

    int updateByPrimaryKey(ImGroupUser record);

    Date selectJoinGroupTime(@Param("groupId") Long groupId, @Param("userId") Long userId);

    List<ImGroupUser> selectImGroupUsers(@Param("groupId") Long groupId);
}