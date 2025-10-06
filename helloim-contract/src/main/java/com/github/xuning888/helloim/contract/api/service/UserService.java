package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.entity.ImUser;

import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 01:35
 */
public interface UserService {

    List<ImUser> getAllUser(String traceId);
}
