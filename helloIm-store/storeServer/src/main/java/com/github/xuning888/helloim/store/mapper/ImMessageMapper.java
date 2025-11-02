package com.github.xuning888.helloim.store.mapper;

import com.github.xuning888.helloim.contract.entity.ImMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImMessageMapper {
    int insertSelective(ImMessage record);

    ImMessage selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(ImMessage record);

    Long selectMaxServerSeq(@Param("msgFrom") Long msgFrom, @Param("msgTo") Long msgTo);
}