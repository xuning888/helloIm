package com.github.xuning888.helloim.message.service.cache;


import com.github.xuning888.helloim.contract.entity.ImGroup;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author xuning
 * @date 2025/12/27 18:08
 */
@Service
public class GroupInfoCacheService {

    // 群聊基础信息的缓存时间
    private final int groupInfoTTL = 7;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public ImGroup getGroupFromCache(Long groupId) {
        String key = RedisKeyUtils.groupInfoKey(groupId);
        Object value = this.redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return (ImGroup) value;
    }

    public void setGroupInfoToCache(ImGroup imGroup) {
        Long groupId = imGroup.getGroupId();
        String key = RedisKeyUtils.groupInfoKey(groupId);
        this.redisTemplate.opsForValue().set(key, imGroup, groupInfoTTL, TimeUnit.DAYS);
    }
}
