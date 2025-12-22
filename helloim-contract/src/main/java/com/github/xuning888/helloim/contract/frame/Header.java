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
    public static final int DEFAULT_HEADER_LENGTH = 20;

    public static final int REQ = 0;

    public static final int RES = 1;

    /**
     * 固定消息头的长度
     */
    private int headerLength;

    /**
     * 0-req, 1-res
     */
    private int req;

    /**
     * 客户端seq
     */
    private int seq;

    /**
     * 消息指令
     */
    private int cmdId;

    /**
     * 消息体的长度
     */
    private int bodyLength;

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

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getReq() {
        return req;
    }

    public void setReq(int req) {
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
        ByteBuffer buff = ByteBuffer.allocate(20)
                .order(ByteOrder.BIG_ENDIAN)
                .put(bytes, 0, DEFAULT_HEADER_LENGTH);
        int headerLength = buff.getInt(0);
        int req = buff.getInt(4);
        int seq = buff.getInt(8);
        int cmdId = buff.getInt(12);
        int bodyLength = buff.getInt(16);
        buff.clear();
        return Header.create(headerLength, req, seq, cmdId, bodyLength);
    }

    public static Header create(int headerLength, int req, int seq, int cmdId, int bodyLength) {
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

    public ByteBuffer toByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_HEADER_LENGTH).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(this.headerLength);
        buffer.putInt(this.req);
        buffer.putInt(this.seq);
        buffer.putInt(this.cmdId);
        buffer.putInt(this.bodyLength);
        return buffer;
    }
}