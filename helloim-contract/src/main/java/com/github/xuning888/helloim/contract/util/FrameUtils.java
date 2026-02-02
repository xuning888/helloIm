package com.github.xuning888.helloim.contract.util;


import com.github.xuning888.helloim.api.protobuf.common.v1.FramePb;
import com.github.xuning888.helloim.api.protobuf.common.v1.HeaderPb;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.google.protobuf.ByteString;

/**
 * @author xuning
 * @date 2026/2/2 01:02
 */
public class FrameUtils {

    public static Frame convertToFrame(FramePb framePb) {
        HeaderPb headerPb = framePb.getHeader();
        Header header = convertToHeader(headerPb);
        byte[] body = framePb.getBody().toByteArray();
        return new Frame(header, body);
    }

    public static FramePb convertToPb(Frame frame) {
        HeaderPb headerPb = convertToHeaderPb(frame.getHeader());
        FramePb.Builder builder = FramePb.newBuilder();
        builder.setHeader(headerPb);
        builder.setBody(ByteString.copyFrom(frame.getBody()));
        return builder.build();
    }

    public static Header convertToHeader(HeaderPb headerPb) {
        Header header = new Header();
        int headerLength = headerPb.getHeaderLength();
        header.setHeaderLength((byte) headerLength);
        header.setReq((byte) headerPb.getReq());
        header.setSeq(headerPb.getSeq());
        header.setCmdId(headerPb.getCmdId());
        header.setBodyLength(headerPb.getBodyLength());
        return header;
    }

    public static HeaderPb convertToHeaderPb(Header header) {
        HeaderPb.Builder builder = HeaderPb.newBuilder();
        builder.setHeaderLength(header.getHeaderLength());
        builder.setReq(header.getReq());
        builder.setSeq(header.getSeq());
        builder.setCmdId(header.getCmdId());
        builder.setBodyLength(header.getBodyLength());
        return builder.build();
    }
}
