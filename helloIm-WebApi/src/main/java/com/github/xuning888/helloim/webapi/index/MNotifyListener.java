package com.github.xuning888.helloim.webapi.index;


import com.github.xuning888.helloim.contract.api.service.DownMsgService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.NotifyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuning
 * @date 2026/1/1 00:54
 */
public class MNotifyListener implements NotifyListener {

    private static final String interfaceName = DownMsgService.class.getName();

    private static final Logger logger = LoggerFactory.getLogger(MNotifyListener.class);

    @Override
    public void notify(List<URL> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return;
        }
        List<URL> targetUrls = new ArrayList<>();
        for (URL url : urls) {
            String name = url.getParameter("interface");
            if (StringUtils.equals(name, interfaceName)) {
                targetUrls.add(url);
            }
        }
        if (CollectionUtils.isEmpty(targetUrls)) {
        }
    }
}
