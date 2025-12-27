package com.github.xuning888.helloim.contract.frame;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author xuning
 * @date 2025/8/2 16:29
 */
public class Header implements Serializable {
    // 固定消息头的长度
    public static final byte DEFAULT_HEADER_LENGTH = 14;

    public static final byte REQ = 0;

    public static final byte RES = 1;

    /**
     * 固定消息头的长度
     */
    private byte headerLength; // 1byte

    /**
     * 0-req, 1-res
     */
    private byte req; // 1byte

    /**
     * 客户端seq
     */
    private int seq; // 4byte

    /**
     * 消息指令
     */
    private int cmdId; // 4byte

    /**
     * 消息体的长度
     */
    private int bodyLength; // 4byte

    public Header() {}

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public int getCmdId() {
        return cmdId;
    }

    public void setCmdId(int cmdId) {
        this.cmdId = cmdId;
    }

    public byte getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(byte headerLength) {
        this.headerLength = headerLength;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public byte getReq() {
        return req;
    }

    public void setReq(byte req) {
        this.req = req;
    }

    public static Header create(ByteBuffer buffer) {
        byte[] array = buffer.array();
        return create(array);
    }

    @Override
    public String toString() {
        return "Header{" +
                "bodyLength=" + bodyLength +
                ", headerLength=" + headerLength +
                ", req=" + req +
                ", seq=" + seq +
                ", cmdId=" + cmdId +
                '}';
    }

    public static Header create(byte[] bytes) {
        if (bytes == null || bytes.length < DEFAULT_HEADER_LENGTH) {
            throw new IllegalArgumentException("bytes is null or bytes length less than 20");
        }
        ByteBuffer buff = ByteBuffer.allocate(DEFAULT_HEADER_LENGTH)
                .order(ByteOrder.BIG_ENDIAN)
                .put(bytes, 0, DEFAULT_HEADER_LENGTH);
        byte headerLength = buff.get(0); // 0
        byte req = buff.get(1); // 1
        int seq = buff.getInt(2); // 2,3,4,5
        int cmdId = buff.getInt(6); // 6,7,8,9
        int bodyLength = buff.getInt(10); // 10,11,12,13
        buff.clear();
        return Header.create(headerLength, req, seq, cmdId, bodyLength);
    }

    public static Header create(byte headerLength, byte req, int seq, int cmdId, int bodyLength) {
        Header header = new Header();
        header.setHeaderLength(headerLength);
        header.setReq(req);
        header.setSeq(seq);
        header.setCmdId(cmdId);
        header.setBodyLength(bodyLength);
        return header;
    }

    public Header copy() {
        return Header.create(this.headerLength, this.req, this.seq, this.cmdId, this.bodyLength);
    }
}