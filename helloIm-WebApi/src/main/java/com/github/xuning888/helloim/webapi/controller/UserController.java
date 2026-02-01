package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.api.convert.ImUserConvert;
import com.github.xuning888.helloim.api.dto.ImUserDto;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImUser;
import com.github.xuning888.helloim.api.protobuf.user.v1.GetAllUserRequest;
import com.github.xuning888.helloim.contract.dto.RestResult;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
import com.github.xuning888.helloim.webapi.rpc.UserServiceRpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author xuning
 * @date 2025/10/4 14:18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceRpc userServiceRpc;

    @GetMapping("/allUser")
    public RestResult<List<ImUserDto>> getAllUser() {
        String traceId = UUID.randomUUID().toString();
        GetAllUserRequest request = GetAllUserRequest.newBuilder().setTraceId(traceId).build();
        List<ImUser> users = userServiceRpc.getAllUser(request);
        List<ImUserDto> imUserDtos = users.stream().map(ImUserConvert::bpConvertDto).collect(Collectors.toList());
        return RestResultUtils.success(imUserDtos);
    }
}
