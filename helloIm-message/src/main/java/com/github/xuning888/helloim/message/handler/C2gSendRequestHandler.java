package com.github.xuning888.helloim.message.handler;


import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.common.v1.GateUser;
import com.github.xuning888.helloim.contract.convert.MessageConvert;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.entity.ImGroup;
import com.github.xuning888.helloim.contract.entity.ImGroupUser;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.kafka.MsgKafkaProducer;
import com.github.xuning888.helloim.contract.kafka.Topics;
import com.github.xuning888.helloim.contract.protobuf.C2gMessage;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.message.rpc.MsgStoreRpc;
import com.github.xuning888.helloim.message.service.OfflineMessageService;
import com.github.xuning888.helloim.message.service.UserGroupCommonService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/12/27 16:25
 */
@Component
public class C2gSendRequestHandler implements MsgHandler {

    private static final Logger logger = LoggerFactory.getLogger(C2gSendRequestHandler.class);

    private final int segmentSize = 50;
    @Resource
    private MsgStoreRpc msgStoreRpc;
    @Resource
    private UserGroupCommonService userGroupCommonService;
    @Resource
    private OfflineMessageService offlineMessageService;

    @Override
    public int getCmdId() {
        return MsgCmd.CmdId.CMD_ID_C2GSEND_VALUE;
    }

    @Override
    public void handleMessage(MsgContext msgContext, String kTraceId) {
        String traceId = msgContext.getTraceId();
        logger.info("handleMessage traceId: {}", traceId);
        Frame frame = msgContext.getFrame();
        String msgFrom = msgContext.getMsgFrom();
        long groupId = msgContext.getGroupId();
        Long msgId = msgContext.getMsgId();
        C2gMessage.C2GSendRequest c2gSendRequest = null;
        try {
            c2gSendRequest = C2gMessage.C2GSendRequest.parseFrom(frame.getBody());
        } catch (Exception ex) {
            logger.error("handleMessage parse c2gSendRequest failed, from: {}, groupId: {}, msgId: {}, traceId: {}",
                    msgFrom, groupId, msgId, traceId, ex);
            return;
        }
        if (c2gSendRequest == null) {
            logger.error("handleMessage c2gSendRequest is null, from: {}, groupId: {}, msgId: {}, traceId: {}",
                    msgFrom, groupId, msgId, traceId);
            return;
        }
        // 检查群聊状态和用户与群聊的关系
        ImGroup imGroup = userGroupCommonService.getGroup(groupId, traceId);
        if (Objects.isNull(imGroup)) {
            logger.error("handleMessage imGroup is null, from: {}, groupId: {}, traceId: {}",
                    msgFrom, groupId, traceId);
            return;
        }
        if (Objects.equals(imGroup.getDelStatus(), 1)) {
            logger.error("handleMessage imGroup is closed, from: {}, groupId: {}, traceId: {}",
                    msgFrom, groupId, traceId);
            return;
        }
        List<ImGroupUser> imGroupUsers = userGroupCommonService.getImGroupUsers(groupId, traceId);
        if (CollectionUtils.isEmpty(imGroupUsers)) {
            logger.error("handleMessage imGroupUsers is empty, from: {}, groupId: {}, traceId: {}",
                    msgFrom, groupId, traceId);
            return;
        }
        if (!checkUserInGroup(imGroupUsers, msgFrom)) {
            return;
        }
        logger.info("handleMessage, from: {}, groupId: {}, msgId: {}, traceId: {}", msgFrom, groupId, msgId, traceId);
        ChatMessage chatMessage = MessageConvert.buildC2GChatMessage(msgContext, c2gSendRequest);
        if (!msgStoreRpc.saveMessage(chatMessage, traceId)) {
            logger.error("handleMessage, saveMessage error from: {}, groupId: {}, msgId: {}, traceId: {}", msgFrom, groupId, msgContext, traceId);
            return;
        }
        // 保存离线消息
        offlineMessageService.saveOfflineMessage(chatMessage, traceId);
        // 构造下行消息的frame
        Frame pushRequestFrame = buildC2GPushRequestFrame(msgContext, c2gSendRequest);
        msgContext.setFrame(pushRequestFrame);
        // 分批构造msgContext, 然后推送到kafka消息总线
        List<List<ImGroupUser>> segments = Lists.partition(imGroupUsers, segmentSize);
        int i = 0;
        for (List<ImGroupUser> segment : segments) {
            msgContext.putContext(MsgContext.GROUP_MEMBER_KEY, convertGateUsers(segment));
            String key = kafkaKey(msgContext, i++);
            MsgKafkaProducer.getInstance().send(Topics.C2G.C2G_PUSH_REQ, key, msgContext);
        }
    }

    // 检查消息发送者是否在群聊中, 如果存在就将msgFrom移除
    private boolean checkUserInGroup(List<ImGroupUser> imGroupUsers, String userId) {
        long userIdInt64 = Long.parseLong(userId);
        Iterator<ImGroupUser> iterator = imGroupUsers.iterator();
        while (iterator.hasNext()) {
            ImGroupUser imGroupUser = iterator.next();
            if (Objects.equals(imGroupUser.getUserId(), userIdInt64)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private Frame buildC2GPushRequestFrame(MsgContext msgContext, C2gMessage.C2GSendRequest request) {
        C2gMessage.C2GPushRequest s2gPushRequest = buildC2GPushRequest(msgContext, request);
        byte[] body = s2gPushRequest.toByteArray();
        Header header = msgContext.getFrame().getHeader().copy();
        header.setReq(Header.REQ);
        header.setCmdId(MsgCmd.CmdId.CMD_ID_C2GPUSH_VALUE);
        header.setBodyLength(body.length);
        return new Frame(header, body);
    }

    private C2gMessage.C2GPushRequest buildC2GPushRequest(MsgContext msgContext, C2gMessage.C2GSendRequest c2gSendRequest) {
        C2gMessage.C2GPushRequest.Builder builder = C2gMessage.C2GPushRequest.newBuilder();
        builder.setFrom(msgContext.getMsgFrom());
        builder.setGroupId(msgContext.getGroupId());
        builder.setContent(c2gSendRequest.getContent());
        builder.setContentType(c2gSendRequest.getContentType());
        builder.setFromUserType(c2gSendRequest.getFromUserType());
        builder.setMsgId(msgContext.getMsgId());
        builder.setTimestamp(msgContext.getTimestamp());
        builder.setServerSeq(msgContext.getServerSeq());
        return builder.build();
    }

    private String kafkaKey(MsgContext msgContext, int i) {
        return msgContext.getMsgFrom() + "_" + msgContext.getGroupId() + "_" + msgContext.getServerSeq() + "_" + i;
    }

    private List<GateUser> convertGateUsers(List<ImGroupUser> imGroupUsers) {
        List<GateUser> gateUsers = new ArrayList<>(imGroupUsers.size());
        for (ImGroupUser imGroupUser : imGroupUsers) {
            GateUser gateUser = GateUser.newBuilder().setUid(imGroupUser.getUserId())
                    .setUserType(imGroupUser.getUserType()).build();
            gateUsers.add(gateUser);
        }
        return gateUsers;
    }
}
