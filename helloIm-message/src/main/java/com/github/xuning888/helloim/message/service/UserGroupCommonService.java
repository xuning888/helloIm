package com.github.xuning888.helloim.message.service;


import com.github.xuning888.helloim.contract.entity.ImGroup;
import com.github.xuning888.helloim.contract.entity.ImGroupUser;
import com.github.xuning888.helloim.message.mapper.ImGroupMapper;
import com.github.xuning888.helloim.message.mapper.ImGroupUserMapper;
import com.github.xuning888.helloim.message.service.cache.GroupInfoCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xuning
 * @date 2025/12/27 17:57
 */
@Service
public class UserGroupCommonService {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupCommonService.class);

    @Resource
    private ImGroupUserMapper imGroupUserMapper;
    @Resource
    private ImGroupMapper imGroupMapper;
    @Resource
    private GroupInfoCacheService groupInfoCacheService;

    public Date getUserJoinGroupTime(Long groupId, String userId, String traceId) {
        logger.info("getUserJoinGroupTime, groupId: {}, userId: {}, traceId: {}", groupId, userId, traceId);
        Date joinGroupTime = imGroupUserMapper.selectJoinGroupTime(groupId, Long.parseLong(userId));
        if (joinGroupTime == null) {
            logger.warn("getUserJoinGroupTime joinGroupTime is null, traceId: {}", traceId);
            return null;
        }
        return joinGroupTime;
    }

    public ImGroup getGroup(Long groupId, String traceId) {
        logger.info("getGroup, groupId: {}, traceId: {}", groupId, traceId);
        ImGroup imGroup = groupInfoCacheService.getGroupFromCache(groupId);
        if (imGroup != null) {
            return imGroup;
        }
        imGroup = this.imGroupMapper.selectByGroupId(groupId);
        if (imGroup == null) {
            logger.warn("getGroup imGroup is null, traceId: {}", traceId);
            return null;
        }
        groupInfoCacheService.setGroupInfoToCache(imGroup);
        return imGroup;
    }

    public List<ImGroupUser> getImGroupUsers(Long groupId, String traceId) {
        logger.info("getImGroupUser, groupId: {}, traceId: {}", groupId, traceId);
        // TODO 查询缓存
        return this.imGroupUserMapper.selectImGroupUsers(groupId);
    }
}
