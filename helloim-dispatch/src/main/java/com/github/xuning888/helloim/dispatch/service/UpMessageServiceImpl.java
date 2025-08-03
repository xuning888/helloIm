package com.github.xuning888.helloim.dispatch.service;

import com.github.xuning888.helloim.contract.api.request.UpMessageReq;
import com.github.xuning888.helloim.contract.api.service.gate.UpMsgService;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.util.GatewayUtils;
import com.github.xuning888.helloim.contract.util.IdGenerator;
import com.github.xuning888.helloim.dispatch.util.UpMessageUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/8/2 19:21
 */
@DubboService
public class UpMessageServiceImpl implements UpMsgService {

    private static final Logger logger = LoggerFactory.getLogger(UpMessageServiceImpl.class);

    private static final String MSGID_SCRIPT =
            "if redis.call('exists', KEYS[1]) == 1 then\n" +
            "    return redis.call('get', KEYS[1])\n" +
            "else\n" +
            "    redis.call('set', KEYS[1], ARGV[1])\n" +
            "    redis.call('expire', KEYS[1], ARGV[2])\n" +
            "    return redis.call('get', KEYS[1])\n" +
            "end";

    private final DispatchService dispatchService;
    private final IdGenerator idGenerator;
    private final RedisTemplate<String, Object> redisTemplate;

    public UpMessageServiceImpl(DispatchService dispatchComponent,
                                RedisTemplate<String, Object> redisTemplate,
                                IdGenerator idGenerator) {
        this.dispatchService = dispatchComponent;
        this.redisTemplate = redisTemplate;
        this.idGenerator = idGenerator;
    }

    @Override
    public String sayHello(String name) {
        return name;
    }

    @Override
    public void sendMessage(UpMessageReq req) {
        // 参数校验
        String traceId = req.getTraceId();
        validParams(req, req.getTraceId());

        Frame frame = req.getFrame();
        int cmdId = frame.getHeader().getCmdId();
        Endpoint endpoint = req.getEndpoint();
        GateUser gateUser = req.getGateUser();
        logger.info("sendMessage user: {}, cmdId: {}, frameSize: {}, traceId: {}", gateUser, cmdId,
                frame.getHeader().getBodyLength(), traceId);

        MsgContext msgContext = new MsgContext();
        msgContext.setTraceId(traceId); // 设置traceId
        msgContext.setMsgFrom(String.valueOf(gateUser.getUid())); // 消息发送者
        msgContext.setEndpoint(endpoint); // 消息来自哪个网关机
        msgContext.setFrame(frame); // 数据帧

        // 分配并设置消息ID
        Long msgId = getMsgId(msgContext);
        msgContext.setMsgId(msgId);

        // 判断消息是否重复
        if (UpMessageUtils.isDuplicate(redisTemplate, msgContext)) {
            logger.warn("sendMessage 上行消息重推, from: {}, cmdId: {}, seq: {}, traceId: {}", gateUser, cmdId,
                    frame.getHeader().getSeq(), traceId);
            // 客户端重推消息，回复ACK
            GatewayUtils.pushResponse(msgContext, endpoint, traceId);
            return;
        }

        // 分发消息
        dispatchService.dispatch(msgContext, traceId);
    }


    private Long getMsgId(MsgContext msgContext) {
        String msgKey = msgIdKey(msgContext);
        Long msgId = idGenerator.nextId();
        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setScriptText(MSGID_SCRIPT);
        script.setResultType(String.class);
        try {
            String msgIdStr = this.redisTemplate.execute(script, Collections.singletonList(msgKey),
                    String.valueOf(msgId), 120);
            msgId = Long.parseLong(msgIdStr);
        } catch (Exception ex) {
            logger.error("getMsgId error, traceId: {}", msgContext.getTraceId(), ex);
        }
        return msgId;
    }

    private String msgIdKey(MsgContext msgContext) {
        Header header = msgContext.getFrame().getHeader();
        int seq = header.getSeq(), cmdId = header.getCmdId();
        String from = msgContext.getMsgFrom();
        return "dispatch_up_message_msgId_" + from + "_" + cmdId + "_" + seq;
    }

    private void validParams(UpMessageReq upMessageReq, String traceId) {
        Frame frame = upMessageReq.getFrame();
        GateUser gateUser = upMessageReq.getGateUser();
        Endpoint endpoint = upMessageReq.getEndpoint();
        if (frame == null) {
            logger.error("validParams frame is null, traceId: {}", traceId);
            throw new IllegalArgumentException("frame is null");
        }
        if (gateUser == null) {
            logger.error("validParams gateUser is null, traceId: {}", traceId);
            throw new IllegalArgumentException("gateUser is null");
        }
        if (endpoint == null) {
            logger.error("validParams endpoint is null, traceId: {}", traceId);
            throw new IllegalArgumentException("endpoint is null");
        }
        if (Objects.isNull(gateUser.getUid())) {
            logger.error("validParams gateUser.uid is null traceId: {}", traceId);
            throw new IllegalArgumentException("validParams gateUser.uid is null");
        }
    }
}