package com.github.xuning888.helloim.delivery.handler;


import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateType;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.contract.meta.ImSession;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.GatewayUtils;
import com.github.xuning888.helloim.delivery.rpc.SessionSvcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xuning
 * @date 2025/12/27 19:43
 */
@Component
public class C2gPushRequestHandler implements MsgDeliverHandler {

    private static final Logger logger = LoggerFactory.getLogger(C2gPushRequestHandler.class);

    @Resource
    private SessionSvcClient sessionSvcClient;

    @Override
    public int getCmdId() {
        return MsgCmd.CmdId.CMD_ID_C2GPUSH_VALUE;
    }

    @Override
    public void handle(MsgContext msgContext, String kTraceId) {
        String traceId = msgContext.getTraceId();
        Frame frame = msgContext.getFrame();
        List<GateUser> groupUsers = msgContext.getFromContext(MsgContext.GROUP_MEMBER_KEY);
        if (CollectionUtils.isEmpty(groupUsers)) {
            logger.error("c2gPushRequestHandler groupUsers is empty, traceId: {}", traceId);
            return;
        }
        Map<GateUser, ImSession> sessionTcp = getSessionTcp(groupUsers, traceId);
        // 找出在线用户和离线用户
        Map<Endpoint, Set<GateUser>> onlineTcpUsers = new HashMap<>();
        Set<GateUser> offlineUsers = new HashSet<>();
        findOnlineUsers(groupUsers, sessionTcp, onlineTcpUsers, offlineUsers);
        logger.info("c2gPushRequestHandler onlineUser: {}, offlineUser: {}, traceId: {}", onlineTcpUsers, offlineUsers, traceId);
        // 在线用户通过长连接网关推送消息
        for (Map.Entry<Endpoint, Set<GateUser>> entry : onlineTcpUsers.entrySet()) {
            Endpoint endpoint = entry.getKey();
            List<GateUser> users = new ArrayList<>(entry.getValue());
            GatewayUtils.pushMessageNeedAck(frame, users, endpoint, traceId);
        }
    }

    private void findOnlineUsers(List<GateUser> users, Map<GateUser, ImSession> sessionMap,
                                 Map<Endpoint, Set<GateUser>> onlineUsers, Set<GateUser> offlineUsers) {
        for (GateUser user : users) {
            ImSession imSession = sessionMap.get(user);
            if (imSession != null) {
                onlineUsers.computeIfAbsent(imSession.getEndpoint(), k -> new HashSet<>()).add(user);
            } else {
                offlineUsers.add(user);
            }
        }
    }

    private Map<GateUser, ImSession> getSessionTcp(List<GateUser> users, String traceId) {
        return sessionSvcClient.batchGetSessionMap(users, GateType.TCP, traceId);
    }
}
