package com.github.xuning888.helloim.gateway.config;

/**
 * @author xuning
 * @date 2025/8/3 22:10
 */
public class GateServerProperties {

    private String protocol;
    private int port;
    private int maxFrameSize;
    private int readTimeoutSeconds;
    private int coreSize;
    private int maxPoolSize;
    private int queueSize;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

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

    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    @Override
    public String toString() {
        return "GateServerProperties{" +
                "protocol='" + protocol + '\'' +
                ", port=" + port +
                ", maxFrameSize=" + maxFrameSize +
                ", readTimeoutSeconds=" + readTimeoutSeconds +
                ", coreSize=" + coreSize +
                ", maxPoolSize=" + maxPoolSize +
                ", queueSize=" + queueSize +
                '}';
    }
}