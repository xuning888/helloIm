package com.github.xuning888.helloim.contract.kafka;

/**
 * @author xuning
 * @date 2025/8/2 23:33
 */
public interface Topics {

    interface C2C {
        String C2C_SEND_REQ = "C2C_SEND_REQ";
        String C2C_PUSH_RES = "C2C_PUSH_RES";
        String C2C_SEND_RES = "C2C_SEND_RES";
    }
}