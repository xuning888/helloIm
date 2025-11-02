package com.github.xuning888.helloim.store.service.impl;

import com.github.xuning888.helloim.contract.contant.CommonConstant;
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

    @Override
    public Long maxServerSeq(String from, String to, String traceId) {
        Long fromMaxServerSeq = selectMaxServerSeq(from, to, traceId);
        Long toMaxServerSeq = selectMaxServerSeq(to, from, traceId);
        if (fromMaxServerSeq == null && toMaxServerSeq == null) {
            return CommonConstant.ERROR_SERVER_SEQ;
        } else if (fromMaxServerSeq == null) {
            return toMaxServerSeq;
        } else if (toMaxServerSeq == null) {
            return fromMaxServerSeq;
        } else {
            return Math.max(fromMaxServerSeq, toMaxServerSeq);
        }
    }

    private Long selectMaxServerSeq(String from, String to, String traceId) {
        long msgFrom = Long.parseLong(from), msgTo = Long.parseLong(to);
        ShardingContextHolder.setDatasource(ShardingUtils.shardingFor4(msgFrom));
        try {
            return this.imMessageMapper.selectMaxServerSeq(msgFrom, msgTo);
        } finally {
            ShardingContextHolder.clear();
        }
    }
}