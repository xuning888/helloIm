package com.github.xuning888.helloim.dispatch.service;

import com.github.xuning888.helloim.contract.api.request.AuthRequest;
import com.github.xuning888.helloim.contract.api.request.LogoutRequest;
import com.github.xuning888.helloim.contract.api.request.UpMessageReq;
import com.github.xuning888.helloim.contract.api.response.AuthResponse;
import com.github.xuning888.helloim.contract.api.response.LogoutResponse;
import com.github.xuning888.helloim.contract.api.service.gate.UpMsgService;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.util.GatewayUtils;
import com.github.xuning888.helloim.contract.util.IdGenerator;
import com.github.xuning888.helloim.dispatch.util.UpMessageUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * @author xuning
 * @date 2025/8/2 19:21
 */
@DubboService
public class UpMessageServiceImpl implements UpMsgService {

    private static final Logger logger = LoggerFactory.getLogger(UpMessageServiceImpl.class);

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
    public AuthResponse auth(AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setGateUser(authRequest.getGateUser());
        authResponse.setSuccess(true);
        authResponse.setTraceId(authRequest.getTraceId());
        return authResponse;
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

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        logger.info("logout request: {}", logoutRequest);
        return new LogoutResponse(logoutRequest.getTraceId(), true, logoutRequest.getGateUser());
    }


    private Long getMsgId(MsgContext msgContext) {
        Long msgId = idGenerator.nextId();
        return UpMessageUtils.getMsgId(this.redisTemplate, msgContext, msgId);
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