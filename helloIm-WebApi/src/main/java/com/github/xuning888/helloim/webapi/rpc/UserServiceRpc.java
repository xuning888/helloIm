package com.github.xuning888.helloim.webapi.rpc;


import com.github.xuning888.helloim.api.protobuf.common.v1.ImUser;
import com.github.xuning888.helloim.api.protobuf.user.v1.GetAllUserRequest;
import com.github.xuning888.helloim.api.protobuf.user.v1.GetAllUserResponse;
import com.github.xuning888.helloim.api.protobuf.user.v1.UserService;
import com.github.xuning888.helloim.contract.contant.RestResultStatus;
import com.github.xuning888.helloim.contract.exception.ImException;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuning
 * @date 2026/2/1 10:15
 */
@Component
public class UserServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceRpc.class);

    @DubboReference
    private UserService userService;

    public List<ImUser> getAllUser(GetAllUserRequest request) {
        logger.info("getAllUser request: {}", request);
        try {
            GetAllUserResponse response = userService.getAllUser(request);
            return response.getUsersList();
        } catch (Exception ex) {
            logger.error("getAllUser errMsg: {}", ex.getMessage(), ex);
            throw new ImException(RestResultStatus.SERVER_ERR, ex);
        }
    }
}
