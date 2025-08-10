package com.github.xuning888.helloim.gateway.core.processor;



import com.github.xuning888.helloim.gateway.core.conn.event.ConnEvent;

/**
 * @author xuning
 * @date 2025/8/10 15:29
 */
public interface Processor {

    void handleConnEvent(ConnEvent connEvent);
}