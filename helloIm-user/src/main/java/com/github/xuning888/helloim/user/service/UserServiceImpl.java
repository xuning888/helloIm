package com.github.xuning888.helloim.user.service;

import com.github.xuning888.helloim.api.protobuf.common.v1.ImUser;
import com.github.xuning888.helloim.api.protobuf.user.v1.DubboUserServiceTriple;
import com.github.xuning888.helloim.api.protobuf.user.v1.GetAllUserRequest;
import com.github.xuning888.helloim.api.protobuf.user.v1.GetAllUserResponse;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;
import com.github.xuning888.helloim.contract.entity.ImUserDo;
import com.github.xuning888.helloim.user.mapper.ImUserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 01:35
 */
@DubboService
public class UserServiceImpl extends DubboUserServiceTriple.UserServiceImplBase {
    
    @Resource
    private ImUserMapper userMapper;

    @Override
    public GetAllUserResponse getAllUser(GetAllUserRequest request) {
        GetAllUserResponse.Builder builder = GetAllUserResponse.newBuilder();
        List<ImUserDo> imUsers = this.userMapper.selectAllUser();
        if (CollectionUtils.isNotEmpty(imUsers)) {
            for (ImUserDo imUser : imUsers) {
                builder.addUsers(convertToGrpcUser(imUser));
            }
        }
        return builder.build();
    }

    private ImUser convertToGrpcUser(ImUserDo user) {
        ImUser.Builder builder = ImUser.newBuilder();
        builder.setUserId(user.getUserId());
        builder.setUserType(user.getUserType());
        builder.setUserName(user.getUserName());
        builder.setIcon(user.getIcon());
        builder.setMobile(user.getMobile());
        builder.setDeviceId(user.getDeviceId());
        builder.setExtra(user.getExtra());
        builder.setUserStatus(user.getUserStatus());
        builder.setCreatedAt(ProtobufUtils.convertTimestamp(user.getCreatedAt()));
        builder.setUpdatedAt(ProtobufUtils.convertTimestamp(user.getUpdatedAt()));
        return builder.build();
    }
}
