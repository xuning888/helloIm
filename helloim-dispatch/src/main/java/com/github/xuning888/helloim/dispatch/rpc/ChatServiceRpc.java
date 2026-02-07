package com.github.xuning888.helloim.dispatch.rpc;


import com.github.xuning888.helloim.api.protobuf.chat.v1.ChatService;
import com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest;
import com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xuning
 * @date 2026/2/7 19:15
 */
@Service
public class ChatServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceRpc.class);

    @DubboReference
    private ChatService chatService;

    public Long serverSeq(String from, String to, ChatType chatType, String traceId) {
        ServerSeqRequest request = ServerSeqRequest.newBuilder().setFrom(from).setTo(to).setChatType(chatType.getType()).setTraceId(traceId).build();
        try {
            ServerSeqResponse response = chatService.serverSeq(request);
            return response.getServerSeq();
        } catch (Exception ex) {
            logger.error("serverSeq, errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
            return CommonConstant.ERROR_SERVER_SEQ;
        }
    }
}
