package com.github.xuning888.helloim.gateway.core.cmd.handler;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.CmdHandler;
import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/8/10 13:34
 */
public class HandlerProxy implements CmdHandler {

    private static final Logger logger = LoggerFactory.getLogger(HandlerProxy.class);

    private final Map<Integer, CmdHandler> handlerMap = new HashMap<>();

    private final DefaultHandler defaultHandler;

    public HandlerProxy(DefaultHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    /**
     * 注册指令
     */
    public void register(int cmdId, CmdHandler cmdHandler) {
        handlerMap.put(cmdId, cmdHandler);
    }

    @Override
    public void handle(CmdEvent cmdEvent) throws Exception {
        try {
            if (cmdEvent instanceof UpCmdEvent) {
                Frame frame = cmdEvent.getFrame();
                // 尝试ACK
                if (cmdEvent.getConn().ack(frame)) {
                    return;
                }
                Header header = frame.getHeader();
                int cmdId = header.getCmdId();
                CmdHandler cmdHandler = handlerMap.get(cmdId);
                if (cmdHandler != null) {
                    cmdHandler.handle(cmdEvent);
                } else {
                    // 透传到路由层
                    defaultHandler.handle(cmdEvent);
                }
            }
        } catch (Exception ex) {
            logger.error("hande upCmdEvent, traceId: {}", cmdEvent.getTraceId(), ex);
            throw ex;
        }
    }
}