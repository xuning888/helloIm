package com.github.xuning888.helloim.contract.gateway;


import com.github.xuning888.helloim.api.protobuf.common.v1.Endpoint;
import com.github.xuning888.helloim.api.protobuf.common.v1.FramePb;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateUser;
import com.github.xuning888.helloim.api.protobuf.gateway.v1.DownMessageRequest;
import com.github.xuning888.helloim.api.protobuf.gateway.v1.DownMessageResponse;
import com.github.xuning888.helloim.api.protobuf.gateway.v1.DownMsgServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 发送下行消息, 这是一个grpc客户端
 *
 * @author xuning
 * @date 2026/2/8 13:43
 */
public class DownMsgSvcClient {

    private static final Logger logger = LoggerFactory.getLogger(DownMsgSvcClient.class);

    private final DownMsgServiceGrpc.DownMsgServiceBlockingStub stub;

    private DownMsgSvcClient(ManagedChannel channel) {
        this.stub = DownMsgServiceGrpc.newBlockingStub(channel);
    }

    public static DownMsgSvcClientBuilder newBuilder() {
        return new DownMsgSvcClientBuilder(ChannelFactoryManager.channelFactory());
    }

    /**
     * 发送下行消息, 返回gateway中不在线的用户
     */
    public List<GateUser> pushMessage(FramePb framePb, List<GateUser> users, boolean needAck, String traceId) {
        if (framePb == null) {
            throw new IllegalArgumentException("framePb is null, traceId: " + traceId);
        }
        if (users == null || users.isEmpty()) {
            throw new IllegalArgumentException("users is empty, traceId: " + traceId);
        }
        logger.info("pushMessage frameSize: {}, users.size: {}, traceId: {}",
                framePb.getSerializedSize(), users.size(), traceId);
        DownMessageRequest request = DownMessageRequest.newBuilder().setNeedAck(needAck)
                .setFrame(framePb).addAllUsers(users).setTraceId(traceId).build();
        try {
            DownMessageResponse downMessageResponse = stub.pushMessage(request);
            return downMessageResponse.getOfflineUsersList();
        } catch (StatusRuntimeException ex) {
            logger.error("pushMessage, errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
            return Collections.emptyList();
        }
    }

    public static class DownMsgSvcClientBuilder {

        private final ChannelFactory channelFactory;

        public DownMsgSvcClientBuilder(ChannelFactory channelFactory) {
            this.channelFactory = channelFactory;
        }

        public DownMsgSvcClient build(Endpoint endpoint) {
            Objects.requireNonNull(endpoint, "endpoint is null");
            String host = endpoint.getHost();
            if (StringUtils.isBlank(host)) {
                throw new IllegalArgumentException("host is blank");
            }
            ManagedChannel channel = channelFactory.getChannel(endpoint);
            return new DownMsgSvcClient(channel);
        }
    }
}
