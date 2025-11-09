package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.contract.api.service.UserGroupService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author xuning
 * @date 2025/11/10 02:34
 */
@DubboService
public class UserGroupServiceImpl implements UserGroupService {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    @Override
    public Date getUserJoinGroupTime(Long groupId, String userId, String traceId) {
        // TODO
        return null;
    }
}
