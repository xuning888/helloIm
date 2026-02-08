package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.api.protobuf.message.v1.DubboUserGroupServiceTriple;
import com.github.xuning888.helloim.api.protobuf.message.v1.GetUserJoinGroupTimeRequest;
import com.github.xuning888.helloim.api.protobuf.message.v1.GetUserJoinGroupTimeResponse;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;
import com.github.xuning888.helloim.contract.api.service.UserGroupService;
import com.github.xuning888.helloim.message.mapper.ImGroupMapper;
import com.github.xuning888.helloim.message.mapper.ImGroupUserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/11/10 02:34
 */
@DubboService
public class UserGroupServiceImpl extends DubboUserGroupServiceTriple.UserGroupServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    @Resource
    private ImGroupMapper imGroupMapper;

    @Resource
    private ImGroupUserMapper imGroupUserMapper;

    @Resource
    private UserGroupCommonService userGroupCommonService;

    @Override
    public GetUserJoinGroupTimeResponse getUserJoinGroupTime(GetUserJoinGroupTimeRequest request) {
        String userId = request.getUserId();
        long groupId = request.getGroupId();
        String traceId = request.getTraceId();
        Date userJoinGroupTime = userGroupCommonService.getUserJoinGroupTime(groupId, userId, traceId);
        GetUserJoinGroupTimeResponse.Builder builder = GetUserJoinGroupTimeResponse.newBuilder();
        if (Objects.nonNull(userJoinGroupTime)) {
            builder.setJoinGroupTime(ProtobufUtils.convertTimestamp(userJoinGroupTime));
        }
        return builder.build();
    }
}
