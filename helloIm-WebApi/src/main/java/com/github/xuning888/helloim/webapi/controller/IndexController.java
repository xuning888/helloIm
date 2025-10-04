package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.contract.dto.RestResult;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
import com.github.xuning888.helloim.webapi.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuning
 * @date 2025/10/2 21:46
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @GetMapping("/iplist")
    public RestResult<Object> iplist() {
        List<String> ipList = indexService.getIpList();
        return RestResultUtils.success(ipList);
    }
}
