package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.contract.api.service.UserGroupService;
import com.github.xuning888.helloim.message.mapper.ImGroupMapper;
import com.github.xuning888.helloim.message.mapper.ImGroupUserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xuning
 * @date 2025/11/10 02:34
 */
@DubboService
public class UserGroupServiceImpl implements UserGroupService {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    @Resource
    private ImGroupMapper imGroupMapper;

    @Resource
    private ImGroupUserMapper imGroupUserMapper;

    @Override
    public Date getUserJoinGroupTime(Long groupId, String userId, String traceId) {
        logger.info("getUserJoinGroupTime, groupId: {}, userId: {}, traceId: {}", groupId, userId, traceId);
        Date joinGroupTime = imGroupUserMapper.selectJoinGroupTime(groupId, Long.parseLong(userId));
        if (joinGroupTime == null) {
            logger.warn("getUserJoinGroupTime joinGroupTime is null, traceId: {}", traceId);
            return null;
        }
        return joinGroupTime;
    }
}
