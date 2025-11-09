package com.github.xuning888.helloim.webapi.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuning
 * @date 2025/10/2 22:06
 */
@Service
public class IndexService {

    public List<String> getIpList() {
        List<String> list = new ArrayList<>();
        list.add("127.0.0.1:9300");
        return list;
    }
}
