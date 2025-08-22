package com.github.xuning888.helloim.contract.meta;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/2 16:13
 */
public class Endpoint implements Serializable {

    private String host;

    private int port;

    private GateType gateType;

    public Endpoint() {}

    public Endpoint(String host, int port, GateType gateType) {
        this.host = host;
        this.port = port;
        this.gateType = gateType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public GateType getGateType() {
        return gateType;
    }

    public void setGateType(GateType gateType) {
        this.gateType = gateType;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", gateType=" + gateType +
                '}';
    }
}