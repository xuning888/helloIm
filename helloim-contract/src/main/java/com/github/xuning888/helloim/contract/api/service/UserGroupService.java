package com.github.xuning888.helloim.contract.api.service;

import java.util.Date;

/**
 * @author xuning
 * @date 2025/11/10 00:18
 */
public interface UserGroupService {

    Date getUserJoinGroupTime(Long groupId, String userId, String traceId);

    boolean checkUserGroup(Long groupId, String userId, String traceId);
}
