package com.github.xuning888.helloim.contract.protobuf;


import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

/**
 * @author xuning
 * @date 2025/12/26 00:33
 */
public class C2gMessageTest {

    @Test
    public void test_createC2CSendRequest() throws InvalidProtocolBufferException {
        C2gMessage.C2GSendRequest.Builder builder = C2gMessage.C2GSendRequest.newBuilder();
        builder.setFrom("1");
        builder.setGroupId(1);
        builder.setContent("helloworld");
        builder.setContentType(0);
        builder.setFromUserType(0);
        C2gMessage.C2GSendRequest c2GSendRequest = builder.build();

        byte[] body = c2GSendRequest.toByteArray();
        Header header = new Header();
        header.setHeaderLength(20);
        header.setReq(1);
        header.setCmdId(MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE);
        header.setSeq(1);
        header.setBodyLength(body.length);
        Frame frame = new Frame(header, body);
        System.out.println(frame);
    }
}
