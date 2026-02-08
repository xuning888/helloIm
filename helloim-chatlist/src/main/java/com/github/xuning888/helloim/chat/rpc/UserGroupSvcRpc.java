package com.github.xuning888.helloim.chat.rpc;


import com.github.xuning888.helloim.api.protobuf.message.v1.GetUserJoinGroupTimeRequest;
import com.github.xuning888.helloim.api.protobuf.message.v1.GetUserJoinGroupTimeResponse;
import com.github.xuning888.helloim.api.protobuf.message.v1.UserGroupService;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xuning
 * @date 2026/2/8 21:21
 */
@Service
public class UserGroupSvcRpc {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupSvcRpc.class);

    @DubboReference
    private UserGroupService userGroupService;

    public Date getUserJoinGroupTime(Long groupId, String userId, String traceId) {

        GetUserJoinGroupTimeRequest request = GetUserJoinGroupTimeRequest.newBuilder()
                .setGroupId(groupId).setUserId(userId).setTraceId(traceId).build();
        try {
            GetUserJoinGroupTimeResponse response = this.userGroupService.getUserJoinGroupTime(request);
            if (response.hasJoinGroupTime()) {
                return ProtobufUtils.convertDate(response.getJoinGroupTime());
            }
            return null;
        } catch (Exception ex) {
            logger.error("getUserJoinGroupTime errMsg: {}", ex.getMessage(), ex);
            return null;
        }
    }
}
