package com.github.xuning888.helloim.contract.util;

import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.protobuf.C2cMessage;

/**
 * @author xuning
 * @date 2025/8/3 13:04
 */
public class MsgUtils {

    /**
     * 构造单聊ACK消息
     */
    public static Frame c2cSendRequestResponse(MsgContext msgContext) {
        // 构造回复的消息体
        C2cMessage.C2cSendResponse.Builder builder = C2cMessage.C2cSendResponse.newBuilder();
        builder.setMsgId(msgContext.getMsgId());
        builder.setServerSeq(msgContext.getServerSeq() == null ? 0 : msgContext.getServerSeq());
        builder.setTimestamp(msgContext.getTimestamp());
        C2cMessage.C2cSendResponse c2cSendResponse = builder.build();
        byte[] body = c2cSendResponse.toByteArray();

        // 构造数据帧
        Header header = msgContext.getFrame().getHeader();
        Header copy = header.copy();
        copy.setReq(Header.RES);
        copy.setBodyLength(body.length);
        Frame frame = new Frame();
        frame.setHeader(copy);
        frame.setBody(body);
        return frame;
    }

    public static Frame emptyFrame(MsgContext msgContext) {
        Frame frame = msgContext.getFrame();
        Header header = frame.getHeader();
        Header copy = header.copy();
        copy.setReq(Header.RES);
        copy.setBodyLength(0);
        return new Frame(copy, null);
    }
}