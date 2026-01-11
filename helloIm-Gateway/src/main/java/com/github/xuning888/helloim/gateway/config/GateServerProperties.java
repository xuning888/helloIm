package com.github.xuning888.helloim.gateway.config;

/**
 * @author xuning
 * @date 2025/8/3 22:10
 */
public class GateServerProperties {

    /**
     * 网关使用的协议
     */
    private String protocol;

    /**
     * 网关监听的端口
     */
    private int port;

    /**
     * 单个frame的最大长度
     */
    private int maxFrameSize;

    /**
     * socket的idle检测, 如果readTimeout时间段内socket没有任何读操作, 就会触发READER_IDLE事件
     */
    private int readTimeoutSeconds;

    /**
     * 上行消息处理线程池的corePoolSize
     */
    private int coreSize;

    /**
     * 上行消息处理线程池的maxPoolSize
     */
    private int maxPoolSize;

    /**
     * 上行消息处理线程池的任务队列的大小
     */
    private int queueSize;

    /**
     * 网关运行模式, 目标是支持proxy(nginx的代理) 或者 NAT模式
     */
    private String proxyMode;

    /**
     * proxyAddr, 默认值为本机IP
     */
    private String proxyAddr;

    /**
     * webhookAddr
     */
    private String webhookAddr;

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

    public String getProxyMode() {
        return proxyMode;
    }

    public void setProxyMode(String proxyMode) {
        this.proxyMode = proxyMode;
    }

    public String getProxyAddr() {
        return proxyAddr;
    }

    public void setProxyAddr(String proxyAddr) {
        this.proxyAddr = proxyAddr;
    }

    public String getWebhookAddr() {
        return webhookAddr;
    }

    public void setWebhookAddr(String webhookAddr) {
        this.webhookAddr = webhookAddr;
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