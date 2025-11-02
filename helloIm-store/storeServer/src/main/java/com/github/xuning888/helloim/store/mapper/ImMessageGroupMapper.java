package com.github.xuning888.helloim.store.mapper;

import com.github.xuning888.helloim.contract.entity.ImMessageGroup;
import org.apache.ibatis.annotations.Param;

public interface ImMessageGroupMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(ImMessageGroup record);

    int insertSelective(ImMessageGroup record);

    ImMessageGroup selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(ImMessageGroup record);

    int updateByPrimaryKey(ImMessageGroup record);


    Long selectMaxServerSeq(@Param("groupId") Long groupId);
}