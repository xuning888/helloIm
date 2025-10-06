package com.github.xuning888.helloim.user.service;

import com.github.xuning888.helloim.contract.api.service.UserService;
import com.github.xuning888.helloim.contract.entity.ImUser;
import com.github.xuning888.helloim.user.mapper.ImUserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 01:35
 */
@DubboService
public class UserServiceImpl implements UserService {
    
    @Resource
    private ImUserMapper userMapper;


    @Override
    public List<ImUser> getAllUser(String traceId) {
        return userMapper.selectAllUser();
    }
}
