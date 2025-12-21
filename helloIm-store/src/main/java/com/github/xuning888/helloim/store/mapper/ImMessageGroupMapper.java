package com.github.xuning888.helloim.store.mapper;

import com.github.xuning888.helloim.contract.entity.ImMessageGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ImMessageGroupMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(ImMessageGroup record);

    int insertSelective(ImMessageGroup record);

    ImMessageGroup selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(ImMessageGroup record);

    Long selectMaxServerSeq(@Param("groupId") Long groupId);

    ImMessageGroup selectLastMessage(@Param("groupId") Long groupId);

    List<ImMessageGroup> selectMessageByServerSeqs(@Param("groupId") Long groupId,
                                                   @Param("serverSeqs") Set<Long> serverSeqs);

    List<ImMessageGroup> selectRecentMessages(@Param("groupId") Long groupId, @Param("limit") Integer limit);
}