package com.github.xuning888.helloim.contract.kafka;

/**
 * @author xuning
 * @date 2025/8/2 23:33
 */
public interface Topics {

    interface C2C {
        String C2C_SEND_REQ = "C2C_SEND_REQ"; // 单聊上行
        String C2C_PUSH_REQ = "C2C_PUSH_REQ"; // 单聊下行
    }

    interface C2G {
        String C2G_SEND_REQ = "C2G_SEND_REQ"; // 群聊上行
        String C2G_PUSH_REQ = "C2G_PUSH_REQ"; // 群聊下行
    }
}