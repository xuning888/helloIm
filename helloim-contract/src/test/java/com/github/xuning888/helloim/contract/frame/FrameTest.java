package com.github.xuning888.helloim.contract.frame;


import com.github.xuning888.helloim.api.protobuf.common.v1.FramePb;
import com.github.xuning888.helloim.contract.protobuf.C2gMessage;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.FrameUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author xuning
 * @date 2026/2/1 21:35
 */
public class FrameTest {


    @Test
    public void test() {
        C2gMessage.C2GSendRequest.Builder builder = C2gMessage.C2GSendRequest.newBuilder();
        builder.setFrom("1");
        builder.setGroupId(1);
        builder.setContent("helloworld");
        builder.setContentType(0);
        builder.setFromUserType(0);
        C2gMessage.C2GSendRequest c2GSendRequest = builder.build();

        byte[] body = c2GSendRequest.toByteArray();
        Header header = new Header();
        header.setHeaderLength((byte) 14);
        header.setReq((byte) 1);
        header.setCmdId(MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE);
        header.setSeq(1);
        header.setBodyLength(body.length);
        Frame frame = new Frame(header, body);
        System.out.println("frame的大小: " + frame.getSerializedSize());
        FramePb framePb = FrameUtils.convertToPb(frame);
        int serializedSize = framePb.getSerializedSize();
        System.out.println("framPb的大小: " + serializedSize);
        Frame convert = FrameUtils.convertToFrame(framePb);
        System.out.println(convert);

        byte[] byteArray = frame.toByteArray();
        System.out.println(Arrays.toString(byteArray));
        byte[] byteArray2 = framePb.toByteArray();
        System.out.println(Arrays.toString(byteArray2));
    }
}
