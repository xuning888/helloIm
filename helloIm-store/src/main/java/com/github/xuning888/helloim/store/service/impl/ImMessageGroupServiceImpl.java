package com.github.xuning888.helloim.store.service.impl;

import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.entity.ImMessageGroup;
import com.github.xuning888.helloim.store.config.ShardingContextHolder;
import com.github.xuning888.helloim.store.mapper.ImMessageGroupMapper;
import com.github.xuning888.helloim.store.service.ImMessageGroupService;
import com.github.xuning888.helloim.store.utils.ShardingUtils;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xuning
 * @date 2025/9/21 17:26
 */
@Service("imMessageGroupServiceImpl")
public class ImMessageGroupServiceImpl implements ImMessageGroupService {

    @Resource
    private ImMessageGroupMapper imMessageGroupMapper;

    @Override
    public int saveMessage(ImMessageGroup imMessageGroup, String traceId) {
        Long groupId = imMessageGroup.getGroupId();
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(groupId));
        try {
            return imMessageGroupMapper.insertSelective(imMessageGroup);
        } finally {
            ShardingContextHolder.clear();
        }
    }

    @Override
    public Long maxServerSeq(String groupId, String traceId) {
        long groupIdInt64 = Long.parseLong(groupId);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(groupIdInt64));
        try {
            Long serverSeq = imMessageGroupMapper.selectMaxServerSeq(groupIdInt64);
            if (serverSeq == null) {
                return 0L;
            }
            return serverSeq;
        } finally {
            ShardingContextHolder.clear();
        }
    }

    @Override
    public ImMessageGroup lastMessage(String groupId, String traceId) {
        long groupIdInt64 = Long.parseLong(groupId);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(groupIdInt64));
        try {
            return imMessageGroupMapper.selectLastMessage(groupIdInt64);
        } finally {
            ShardingContextHolder.clear();
        }
    }
}