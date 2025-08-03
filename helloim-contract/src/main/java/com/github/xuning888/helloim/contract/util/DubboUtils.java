package com.github.xuning888.helloim.contract.util;

import com.github.xuning888.helloim.contract.api.service.gate.DownMsgService;
import com.github.xuning888.helloim.contract.api.service.gate.UpMsgService;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MethodConfig;
import org.apache.dubbo.config.ReferenceConfig;
//import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuning
 * @date 2025/8/3 12:44
 */
public class DubboUtils {

    private static final Logger logger = LoggerFactory.getLogger(DubboUtils.class);

    private static final Map<String, ReferenceConfig<DownMsgService>> cache = new ConcurrentHashMap<>();

    public static DownMsgService downMsgService(String application, Endpoint endpoint, String traceId) {
        String key = key(endpoint);
        ReferenceConfig<DownMsgService> ref = cache.get(key);
        if (ref == null) {
            synchronized (DubboUtils.class) {
                ref = cache.get(key);
                if (ref == null) {
                    ref = buildReferenceConfig(application, endpoint);
                    cache.put(key, ref);
                }
            }
        }
        DownMsgService downMsgService = null;
        try {
            downMsgService = ref.get();
            if (downMsgService == null) {
                logger.error("downMsgService 获取downMsgService对象失败, traceId: {}", traceId);
            }
        } catch (Exception ex) {
            logger.error("downMsgService 获取downMsgService对象失败2, traceId: {}", traceId, ex);
        }

        return downMsgService;
    }


    private static ReferenceConfig<DownMsgService> buildReferenceConfig(String application, Endpoint endpoint) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(application);
        ReferenceConfig<DownMsgService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setInterface(DownMsgService.class);
        String serviceUrl = serviceUrl(DownMsgService.class.getName(), endpoint);
        referenceConfig.setUrl(serviceUrl);
        referenceConfig.setTimeout(2000);
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("pushMessage");
        methodConfig.setReturn(false);
        referenceConfig.setMethods(Collections.singletonList(methodConfig));
        return referenceConfig;
    }

    private static String serviceUrl(String interfaceName, Endpoint endpoint) {
        return String.format("tri://%s:%d/%s?reconnect=false&send.reconnect=true",
                endpoint.getHost(), endpoint.getPort(), interfaceName);
    }


    private static String key(Endpoint endpoint) {
        return String.format("%s_%d", endpoint.getHost(), endpoint.getPort());
    }
}