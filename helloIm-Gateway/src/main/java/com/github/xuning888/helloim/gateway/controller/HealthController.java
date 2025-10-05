package com.github.xuning888.helloim.gateway.controller;

import com.github.xuning888.helloim.contract.dto.RestResult;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuning
 * @date 2025/10/5 19:51
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    private final SessionManager sessionManager;

    public HealthController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @GetMapping("/connCont")
    public RestResult<Object> connCont() {
        int cnt = sessionManager.count();
        return RestResultUtils.success(cnt);
    }
}
