package com.github.xuning888.helloim.gateway.core.cmd.handler;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.CmdHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuning
 * @date 2025/8/10 13:28
 */
public class EchoHandler implements CmdHandler {

    private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);

    @Override
    public void handle(CmdEvent cmdEvent) throws Exception {
        Frame request = cmdEvent.getFrame();
        String traceId = cmdEvent.getTraceId();
        logger.info("echoHandler, bodySize: {}, msg: {}, traceId: {}", request.getBody().length,
                new String(request.getBody()), traceId);
        cmdEvent.getConn().write(request, traceId);
    }
}