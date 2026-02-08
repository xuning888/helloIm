package com.github.xuning888.helloim.contract.gateway;


/**
 * @author xuning
 * @date 2026/2/8 15:04
 */
public class DownMsgSvcProperties {

    /**
     * grpc的channel保持活跃的时间。
     * 客户端在keepaliveTimeSecond没有发送rpc请求时, 会自动发送一个ping来保持连接活跃
     */
    private int keepaliveTimeSecond;

    /**
     * ping 响应超时时间
     */
    private int keepaliveTimeoutSecond;

    /**
     * 空闲连接的超时时间, 默认5min
     */
    private int idleTimeoutSecond = 300;

    /**
     * 获取连接的最大重试次数, 默认3次
     */
    private int maxRetryAttempts = 3;

    public int getKeepaliveTimeSecond() {
        return keepaliveTimeSecond;
    }

    public void setKeepaliveTimeSecond(int keepaliveTimeSecond) {
        this.keepaliveTimeSecond = keepaliveTimeSecond;
    }

    public int getKeepaliveTimeoutSecond() {
        return keepaliveTimeoutSecond;
    }

    public void setKeepaliveTimeoutSecond(int keepaliveTimeoutSecond) {
        this.keepaliveTimeoutSecond = keepaliveTimeoutSecond;
    }

    public int getIdleTimeoutSecond() {
        return idleTimeoutSecond;
    }

    public void setIdleTimeoutSecond(int idleTimeoutSecond) {
        this.idleTimeoutSecond = idleTimeoutSecond;
    }

    public int getMaxRetryAttempts() {
        return maxRetryAttempts;
    }

    public void setMaxRetryAttempts(int maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
    }

    @Override
    public String toString() {
        return "DownMsgSvcProperties{" +
                "keepaliveTimeSecond=" + keepaliveTimeSecond +
                ", keepaliveTimeoutSecond=" + keepaliveTimeoutSecond +
                ", idleTimeoutSecond=" + idleTimeoutSecond +
                ", maxRetryAttempts=" + maxRetryAttempts +
                '}';
    }
}
