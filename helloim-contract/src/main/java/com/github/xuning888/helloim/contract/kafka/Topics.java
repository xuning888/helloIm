package com.github.xuning888.helloim.contract.kafka;

/**
 * @author xuning
 * @date 2025/8/2 23:33
 */
public interface Topics {

    interface C2C {
        String C2C_SEND_REQ = "C2C_SEND_REQ"; // 单聊上行
        String C2C_PUSH_RES = "C2C_PUSH_RES"; // 单聊下行
        String C2C_SEND_RES = "C2C_SEND_RES"; // 单聊ACK上行
    }
}