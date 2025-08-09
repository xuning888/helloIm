package com.github.xuning888.helloim.gateway.config;

import com.github.xuning888.helloim.contract.meta.Endpoint;
import org.apache.dubbo.common.utils.NetUtils;

/**
 * @author xuning
 * @date 2025/8/3 22:29
 */
public class GateAddr {

    private final int port;

    private final String host;

    public GateAddr(int port) {
        this.port = port;
        this.host = NetUtils.getLocalHost();
    }

    private volatile Endpoint endpoint;

    public Endpoint endpoint() {
        if (endpoint != null) {
            return endpoint;
        }
        synchronized (GateAddr.class) {
            if (endpoint != null) {
                return endpoint;
            }
            endpoint = new Endpoint();
            endpoint.setPort(this.port);
            endpoint.setHost(this.host);
        }
        return endpoint;
    }
}