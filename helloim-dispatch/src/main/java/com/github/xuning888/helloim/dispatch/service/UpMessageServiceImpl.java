package com.github.xuning888.helloim.dispatch.service;


import com.github.xuning888.helloim.api.protobuf.common.v1.Endpoint;
import com.github.xuning888.helloim.api.protobuf.common.v1.FramePb;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateUser;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImSession;
import com.github.xuning888.helloim.api.protobuf.gateway.v1.*;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.FrameUtils;
import com.github.xuning888.helloim.contract.util.GatewayGrpcUtils;
import com.github.xuning888.helloim.contract.util.IdGenerator;
import com.github.xuning888.helloim.dispatch.rpc.SessionServiceRpc;
import com.github.xuning888.helloim.dispatch.util.UpMessageUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author xuning
 * @date 2026/2/8 17:43
 */
@DubboService
public class UpMessageServiceImpl extends DubboUpMsgServiceTriple.UpMsgServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(UpMessageServiceImpl.class);

    @Resource
    private DispatchService dispatchService;
    @Resource
    private IdGenerator idGenerator;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private SessionServiceRpc sessionServiceRpc;

    @Override
    public AuthResponse auth(AuthRequest request) {
        AuthResponse.Builder builder = AuthResponse.newBuilder();
        builder.setGateUser(request.getGateUser());
        builder.setSuccess(true);
        builder.setTraceId(request.getTraceId());
        // TODO 校验token
        // 保存session
        this.saveSession(request);
        return builder.build();
    }

    @Override
    public UpMessageResponse sendMessage(UpMessageRequest req) {
        String traceId = req.getTraceId();
        validParams(req);

        FramePb framePb = req.getFrame();
        int cmdId = framePb.getHeader().getCmdId();
        Endpoint endpoint = req.getEndpoint();
        GateUser gateUser = req.getGateUser();
        logger.info("sendMessage user: {}, cmdId: {}, frameSize: {}, traceId: {}", gateUser, cmdId,
                framePb.getHeader().getBodyLength(), traceId);

        if (
                cmdId == MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE || // 单聊上行
                cmdId == MsgCmd.CmdId.CMD_ID_C2GSEND_VALUE    // 群聊上行
        ) {
            MsgContext msgContext = new MsgContext();
            msgContext.setTimestamp(System.currentTimeMillis()); // 接收到消息的时间
            msgContext.setTraceId(traceId); // 设置traceId
            msgContext.setMsgFrom(String.valueOf(gateUser.getUid())); // 消息发送者
            msgContext.setFromUserType(gateUser.getUserType()); // 消息发送者到用户类型
            msgContext.setEndpoint(endpoint); // 消息来自哪个网关机
            msgContext.setSessionId(gateUser.getSessionId());
            msgContext.setFrame(FrameUtils.convertToFrame(framePb)); // 数据帧

            // 分配并设置消息ID
            Long msgId = getMsgId(msgContext);
            msgContext.setMsgId(msgId);

            // 判断消息是否重复
            if (UpMessageUtils.isDuplicate(redisTemplate, msgContext)) {
                logger.warn("sendMessage 上行消息重推, from: {}, cmdId: {}, seq: {}, traceId: {}", gateUser, cmdId,
                        framePb.getHeader().getSeq(), traceId);
                // 客户端重推消息，回复ACK
                GatewayGrpcUtils.pushResponse(msgContext, endpoint, traceId);
                return UpMessageResponse.newBuilder().setTraceId(traceId).build();
            }

            // 分发消息
            dispatchService.dispatch(msgContext, traceId);
        }
        return UpMessageResponse.newBuilder().setTraceId(traceId).build();
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        logger.info("logout request: {}, traceId: {}", ProtobufUtils.toJson(logoutRequest), logoutRequest.getTraceId());
        return doLogout(logoutRequest);
    }

    private Long getMsgId(MsgContext msgContext) {
        Long msgId = idGenerator.nextId();
        return UpMessageUtils.getMsgId(this.redisTemplate, msgContext, msgId);
    }

    private void validParams(UpMessageRequest upMessageRequest) {
        String traceId = upMessageRequest.getTraceId();
        if (!upMessageRequest.hasFrame()) {
            logger.error("validParams frame is null, traceId: {}", traceId);
            throw new IllegalArgumentException("frame is null");
        }
        if (!upMessageRequest.hasGateUser()) {
            logger.error("validParams gateUser is null, traceId: {}", traceId);
            throw new IllegalArgumentException("gateUser is null");
        }
        if (!upMessageRequest.hasEndpoint()) {
            logger.error("validParams endpoint is null, traceId: {}", traceId);
            throw new IllegalArgumentException("endpoint is null");
        }
        GateUser gateUser = upMessageRequest.getGateUser();
        if (gateUser.getUid() <= 0) {
            logger.error("validParams gateUser.uid is invalid traceId: {}", traceId);
            throw new IllegalArgumentException("validParams gateUser.uid is invalid");
        }
    }

    private LogoutResponse doLogout(LogoutRequest logoutRequest) {

        // 删除session
        removeSession(logoutRequest);

        // 删除成功
        LogoutResponse.Builder builder = LogoutResponse.newBuilder();
        builder.setGateUser(logoutRequest.getGateUser());
        builder.setSuccess(true);
        builder.setTraceId(logoutRequest.getTraceId());
        return builder.build();
    }

    private void removeSession(LogoutRequest logoutRequest) {
        GateUser gateUser = logoutRequest.getGateUser();
        String traceId = logoutRequest.getTraceId();
        Endpoint endpoint = logoutRequest.getEndpoint();
        ImSession.Builder builder = ImSession.newBuilder();
        builder.setSessionId(logoutRequest.getSessionId());
        builder.setGateUser(gateUser);
        builder.setEndpoint(endpoint);
        this.sessionServiceRpc.removeSession(builder.build(), traceId);
    }

    /**
     * 保存session
     */
    private void saveSession(AuthRequest request) {
        ImSession.Builder builder = ImSession.newBuilder();
        builder.setEndpoint(request.getEndpoint());
        builder.setGateUser(request.getGateUser());
        builder.setSessionId(request.getSessionId());

        sessionServiceRpc.saveSession(builder.build(), request.getTraceId());
    }
}