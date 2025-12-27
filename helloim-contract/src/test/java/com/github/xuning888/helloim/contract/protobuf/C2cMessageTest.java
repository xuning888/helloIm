package com.github.xuning888.helloim.contract.protobuf;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

/**
 * @author xuning
 * @date 2025/8/3 19:41
 */
public class C2cMessageTest {


    @Test
    public void test_createC2CSendRequest() throws InvalidProtocolBufferException {
        C2cMessage.C2cSendRequest.Builder builder = C2cMessage.C2cSendRequest.newBuilder();
        builder.setFrom("1");
        builder.setTo("2");
        builder.setContent("helloworld");
        builder.setContentType(0);
        C2cMessage.C2cSendRequest c2cSendRequest = builder.build();

        byte[] body = c2cSendRequest.toByteArray();

        Header header = new Header();
        header.setHeaderLength((byte) 14);
        header.setReq((byte) 1);
        header.setCmdId(MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE);
        header.setSeq(1);
        header.setBodyLength(body.length);

        Frame frame = new Frame(header, body);
        System.out.println(frame);

        body = new byte[]{
                10, 1, 57, 18, 1, 49, 26, 1, 49, 40, -92, -95, -128, -28, -80, -2, -78, 125, 48, 61, 56, -82, -75, -106, -65, -79, 51
        };
        System.out.println(body.length);
        C2cMessage.C2cPushRequest c2cPushRequest = C2cMessage.C2cPushRequest.parseFrom(body);
        System.out.println(c2cPushRequest);
    }
}