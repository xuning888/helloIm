package com.github.xuning888.helloim.store.service.impl;

import com.github.xuning888.helloim.contract.entity.ImMessage;
import com.github.xuning888.helloim.store.config.ShardingContextHolder;
import com.github.xuning888.helloim.store.mapper.ImMessageMapper;
import com.github.xuning888.helloim.store.service.ImMessageService;
import com.github.xuning888.helloim.store.utils.ShardingUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xuning
 * @date 2025/9/21 16:15
 */
@Service("imMessageServiceImpl")
public class ImMessageServiceImpl implements ImMessageService {

    @Resource
    private ImMessageMapper imMessageMapper;


    @Override
    public int saveMessage(ImMessage imMessage, String traceId) {
        Long msgFrom = imMessage.getMsgFrom();
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(msgFrom));
        try {
            return this.imMessageMapper.insertSelective(imMessage);
        } finally {
            ShardingContextHolder.clear();
        }
    }
}