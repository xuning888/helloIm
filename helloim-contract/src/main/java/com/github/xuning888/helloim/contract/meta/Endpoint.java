package com.github.xuning888.helloim.contract.meta;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/2 16:13
 */
public class Endpoint implements Serializable {

    private String host;

    private int port;

    public Endpoint() {}

    public Endpoint(String host, int port) {
        this.host = host;
        this.port = port;
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

    @Override
    public String toString() {
        return String.format("endpoint: %s:%d\n", host, port);
    }
}