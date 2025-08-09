package com.github.xuning888.helloim.gateway.config;

/**
 * @author xuning
 * @date 2025/8/3 22:10
 */
public class GateServerProperties {

    private int port;
    private int maxFrameSize;
    private int readTimeoutSeconds;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxFrameSize() {
        return maxFrameSize;
    }

    public void setMaxFrameSize(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    public int getReadTimeoutSeconds() {
        return readTimeoutSeconds;
    }

    public void setReadTimeoutSeconds(int readTimeoutSeconds) {
        this.readTimeoutSeconds = readTimeoutSeconds;
    }

    @Override
    public String toString() {
        return "GateServerProperties{" +
                "port=" + port +
                ", maxFrameSize=" + maxFrameSize +
                ", readTimeoutSeconds=" + readTimeoutSeconds +
                '}';
    }
}