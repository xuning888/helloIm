package com.github.xuning888.helloim.gateway.core.cmd.handler;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.protobuf.Echo;
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
        Echo.EchoRequest echoRequest = Echo.EchoRequest.parseFrom(request.getBody());
        logger.info("echoHandler, bodySize: {}, msg: {}, traceId: {}", request.getBody().length,
                echoRequest, traceId);
        Echo.EchoResponse response = Echo.EchoResponse.newBuilder().setMsg(echoRequest.getMsg()).build();
        byte[] byteArray = response.toByteArray();
        Header h = request.getHeader().copy();
        h.setBodyLength(byteArray.length);
        Frame reply = new Frame(h, byteArray);
        cmdEvent.getConn().write(reply, traceId);
    }
}