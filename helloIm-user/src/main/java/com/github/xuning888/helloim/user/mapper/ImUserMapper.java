package com.github.xuning888.helloim.user.mapper;

import com.github.xuning888.helloim.contract.entity.ImUserDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImUserMapper {
    int insert(ImUserDo record);

    int insertSelective(ImUserDo record);

    ImUserDo selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(ImUserDo record);

    int updateByPrimaryKey(ImUserDo record);

    List<ImUserDo> selectAllUser();
}