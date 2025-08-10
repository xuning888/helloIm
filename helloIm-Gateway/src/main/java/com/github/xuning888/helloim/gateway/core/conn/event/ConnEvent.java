package com.github.xuning888.helloim.gateway.core.conn.event;

import com.github.xuning888.helloim.gateway.core.conn.Conn;

import java.io.Serializable;

/**
 * 所有发生在长连接中的事件
 * @author xuning
 * @date 2025/8/10 10:53
 */
public interface ConnEvent extends Serializable {

    /**
     * 获取时间关联的长连接
     * @return conn
     */
    Conn getConn();

    /**
     * 获取traceId
     * @return traceId
     */
    String getTraceId();
}
