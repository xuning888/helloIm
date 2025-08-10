package com.github.xuning888.helloim.contract.contant;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/10 15:43
 */
public enum GateSessionState implements Serializable {
    // peer 主动断开
    CLOSE,
    // 未知原因断开
    ERROR,
    // 心跳超时
    TIMEOUT,
    // 登出
    LOGOUT
}