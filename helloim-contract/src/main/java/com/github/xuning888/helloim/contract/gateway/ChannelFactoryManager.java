package com.github.xuning888.helloim.contract.gateway;


/**
 * @author xuning
 * @date 2026/2/8 14:27
 */
public class ChannelFactoryManager {

    private static volatile ChannelFactory instance;

    public static void init(DownMsgSvcProperties downMsgSvcProperties) {
        if (instance != null) {
            return;
        }
        synchronized (ChannelFactoryManager.class) {
            if (instance != null) {
                return;
            }
            int keepaliveTimeSecond = downMsgSvcProperties.getKeepaliveTimeSecond();
            int keepaliveTimeoutSecond = downMsgSvcProperties.getKeepaliveTimeoutSecond();
            int idleTimeoutSecond = downMsgSvcProperties.getIdleTimeoutSecond();
            int maxRetryAttempts = downMsgSvcProperties.getMaxRetryAttempts();
            instance = new ChannelFactory(keepaliveTimeSecond, keepaliveTimeoutSecond, idleTimeoutSecond, maxRetryAttempts);
        }
    }

    public static void shutdown() throws Exception {
        if (instance != null) {
            instance.close();
        }
    }

    static ChannelFactory channelFactory() {
        if (instance == null) {
            throw new IllegalArgumentException("channelFactory is null");
        }
        return instance;
    }
}
