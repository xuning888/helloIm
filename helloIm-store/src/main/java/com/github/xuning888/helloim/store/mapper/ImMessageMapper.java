package com.github.xuning888.helloim.store.mapper;

import com.github.xuning888.helloim.contract.entity.ImMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ImMessageMapper {
    int insertSelective(ImMessage record);

    ImMessage selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(ImMessage record);

    Long selectMaxServerSeq(@Param("msgFrom") Long msgFrom, @Param("msgTo") Long msgTo);

    ImMessage lastMessage(@Param("msgFrom") Long msgFrom, @Param("msgTo") Long msgTo);

    List<ImMessage> selectMessageBySeqs(@Param("msgFrom") Long msgFrom,
                                          @Param("msgTo") Long msgTo,
                                          @Param("serverSeqs") Set<Long> serverSeqs);

    List<ImMessage> selectRecentMessages(@Param("msgFrom") Long msgFrom,
                                         @Param("msgTo") Long msgTo,
                                         @Param("limit") Integer limit);
}