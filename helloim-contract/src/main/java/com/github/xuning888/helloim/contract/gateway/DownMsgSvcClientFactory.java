package com.github.xuning888.helloim.contract.gateway;


import com.github.xuning888.helloim.api.protobuf.common.v1.Endpoint;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuning
 * @date 2026/2/11 22:49
 */
public class DownMsgSvcClientFactory {

    private static final ConcurrentHashMap<Endpoint, DownMsgSvcClient> clientCache = new ConcurrentHashMap<>();

    public static DownMsgSvcClient getClient(Endpoint endpoint) {
        return clientCache.compute(endpoint, (k, existingClient) -> {
            if (existingClient != null) {
                return existingClient;
            }
            return DownMsgSvcClient.newBuilder().build(endpoint);
        });
    }
}
