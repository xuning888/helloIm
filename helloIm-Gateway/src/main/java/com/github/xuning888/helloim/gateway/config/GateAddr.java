package com.github.xuning888.helloim.gateway.config;

import com.github.xuning888.helloim.api.protobuf.common.v1.Endpoint;
import com.github.xuning888.helloim.contract.contant.GateType;
import org.apache.dubbo.common.utils.NetUtils;

/**
 * @author xuning
 * @date 2025/8/3 22:29
 */
public class GateAddr {

    private final int port;

    private final String host;

    private final String protocol;

    private volatile Endpoint endpoint;

    public GateAddr(int port, String protocol) {
        this.port = port;
        this.host = NetUtils.getLocalHost();
        this.protocol = protocol;
    }

    public Endpoint endpoint() {
        if (endpoint != null) {
            return endpoint;
        }
        synchronized (GateAddr.class) {
            if (endpoint != null) {
                return endpoint;
            }
            Endpoint.Builder builder = Endpoint.newBuilder();
            builder.setPort(this.port);
            builder.setHost(this.host);
            builder.setGateType(convert2Pb(GateType.parseGateType(protocol)));
            this.endpoint = builder.build();
        }
        return endpoint;
    }

    private com.github.xuning888.helloim.api.protobuf.common.v1.GateType convert2Pb(GateType gateType) {
        if (gateType == null) {
            throw new NullPointerException("gateType is null");
        }
        if (GateType.TCP.equals(gateType)) {
            return com.github.xuning888.helloim.api.protobuf.common.v1.GateType.TCP;
        }
        throw new IllegalArgumentException("unknown gateType: " + gateType);
    }
}