package com.github.xuning888.helloim.dispatch.service;

import com.github.xuning888.helloim.contract.api.request.AuthRequest;
import com.github.xuning888.helloim.contract.api.request.LogoutRequest;
import com.github.xuning888.helloim.contract.api.request.UpMessageReq;
import com.github.xuning888.helloim.contract.api.response.AuthResponse;
import com.github.xuning888.helloim.contract.api.response.LogoutResponse;
import com.github.xuning888.helloim.contract.api.service.SessionService;
import com.github.xuning888.helloim.contract.api.service.UpMsgService;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.meta.ImSession;
import com.github.xuning888.helloim.contract.util.GatewayUtils;
import com.github.xuning888.helloim.contract.util.IdGenerator;
import com.github.xuning888.helloim.dispatch.util.UpMessageUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/8/2 19:21
 */
@DubboService
public class UpMessageServiceImpl implements UpMsgService {

    private static final Logger logger = LoggerFactory.getLogger(UpMessageServiceImpl.class);

    @Resource
    private DispatchService dispatchService;
    @Resource
    private IdGenerator idGenerator;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @DubboReference
    private SessionService sessionService;


    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setGateUser(authRequest.getGateUser());
        authResponse.setSuccess(true);
        authResponse.setTraceId(authRequest.getTraceId());
        // TODO 校验token
        // 保存session
        saveSession(authRequest);
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
        logger.info("logout request: {}, traceId: {}", logoutRequest, logoutRequest.getTraceId());
        LogoutResponse logoutResponse = doLogout(logoutRequest);
        logger.info("logout response: {}, traceId: {}", logoutRequest, logoutRequest.getTraceId());
        return logoutResponse;
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

    private LogoutResponse doLogout(LogoutRequest logoutRequest) {

        // TODO 删除token

        // 删除session
        removeSession(logoutRequest);

        // 删除成功
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setGateUser(logoutRequest.getGateUser());
        logoutResponse.setSuccess(true);
        logoutResponse.setTraceId(logoutRequest.getTraceId());
        return logoutResponse;
    }

    private void removeSession(LogoutRequest logoutRequest) {
        ImSession imSession = new ImSession();
        imSession.setGateUser(logoutRequest.getGateUser());
        imSession.setEndpoint(logoutRequest.getEndpoint());
        this.sessionService.removeSession(imSession, logoutRequest.getTraceId());
    }

    /**
     * 保存session
     */
    private void saveSession(AuthRequest authRequest) {
        ImSession imSession = new ImSession();
        imSession.setEndpoint(authRequest.getEndpoint());
        imSession.setGateUser(authRequest.getGateUser());
        imSession.setSessionId(authRequest.getSessionId());
        sessionService.saveSession(imSession, authRequest.getTraceId());
    }
}