package com.github.xuning888.helloim.contract.contant;


/**
 * @author xuning
 * @date 2025/12/28 17:38
 */
public class MetricsConstant {

    /**
     * 上行消息数量
     */
    public static final String UP_REQUEST_COUNT_METRICS = "up.request.count";
    /**
     * 上行消息流量
     */
    public static final String UP_REQUEST_BYTE_COUNT_METRICS = "up.request.byte.count";
    /**
     * 下行消息数量
     */
    public static final String DOWN_REQUEST_COUNT_METRICS = "down.request.count";
    /**
     * 下行消息流量
     */
    public static final String DOWN_REQUEST_BYTE_COUNT_METRICS = "down.request.byte.count";

    /**
     * 消息延迟
     */
    public static final String MESSAGE_LATENCY = "message.latency";
}
