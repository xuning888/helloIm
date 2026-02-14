package com.github.xuning888.helloim.gateway.core.cmd.handler;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.protobuf.Echo;
import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.CmdHandler;
import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
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
        String traceId = cmdEvent.getTraceId();
        // 解析echoRequest
        Echo.EchoRequest echoRequest = parseEchoRequest(cmdEvent);
        if (echoRequest == null) {
            logger.error("EchoHandler echoRequest is null, traceId: {}", traceId);
            return;
        }
        logger.info("EchoHandler, echoRequest: {}, traceId: {}", echoRequest, traceId);

        // 构造echoResponse
        Frame echoResponse = buildEchoResponse(cmdEvent.getFrame(), echoRequest.getMsg());

        // 获取pipe
        MsgPipeline msgPipeline = cmdEvent.getConn().getMsgPipeline();

        // 构造下行事件
        DownCmdEvent downCmdEvent = new DownCmdEvent(echoResponse, cmdEvent.getConn(), traceId);
        try {
            msgPipeline.sendDown(downCmdEvent);
        } catch (Exception ex) {
            logger.error("EchoHandler, sendDown failed, traceId: {}", traceId, ex);
        }
    }

    private Echo.EchoRequest parseEchoRequest(CmdEvent cmdEvent) {
        Frame frame = cmdEvent.getFrame();
        String traceId = cmdEvent.getTraceId();
        Echo.EchoRequest echoRequest = null;
        try {
            echoRequest = Echo.EchoRequest.parseFrom(frame.getBody());
        } catch (Exception ex) {
            logger.error("parse echoRequest failed, traceId: {}", traceId, ex);
        }
        return echoRequest;
    }

    private Frame buildEchoResponse(Frame frame, String msg) {
        Echo.EchoResponse echoResponse = buildEchoResponse(msg);
        Header header = frame.getHeader().copy();
        header.setReq(Header.RES);
        byte[] body = echoResponse.toByteArray();
        header.setBodyLength(body.length);
        return new Frame(header, body);
    }

    private Echo.EchoResponse buildEchoResponse(String msg) {
        Echo.EchoResponse.Builder builder = Echo.EchoResponse.newBuilder();
        builder.setMsg(msg);
        return builder.build();
    }
}