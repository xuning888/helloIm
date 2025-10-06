package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.contract.api.service.UserService;
import com.github.xuning888.helloim.contract.dto.RestResult;
import com.github.xuning888.helloim.contract.entity.ImUser;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author xuning
 * @date 2025/10/4 14:18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference
    private UserService userService;

    @GetMapping("/allUser")
    public RestResult<List<ImUser>> getAllUser() {
        String traceId = UUID.randomUUID().toString();
        return RestResultUtils.success(userService.getAllUser(traceId));
    }
}
