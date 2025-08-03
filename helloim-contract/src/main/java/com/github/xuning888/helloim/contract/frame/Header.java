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
    public static int DEFAULT_HEADER_LENGTH = 20;

    /**
     * 固定消息头的长度
     */
    private int headerLength;

    /**
     * 客户端客户端版本号
     */
    private int clientVersion;

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

    public int getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
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

    @Override
    public String toString() {
        return "Header{" +
                "bodyLength=" + bodyLength +
                ", headerLength=" + headerLength +
                ", clientVersion=" + clientVersion +
                ", seq=" + seq +
                ", cmdId=" + cmdId +
                '}';
    }

    public static Header create(ByteBuffer buffer) {
        byte[] array = buffer.array();
        return create(array);
    }

    public static Header create(byte[] bytes) {
        if (bytes == null || bytes.length < DEFAULT_HEADER_LENGTH) {
            throw new IllegalArgumentException("bytes is null or bytes length less than 20");
        }
        ByteBuffer buff = ByteBuffer.allocate(20)
                .order(ByteOrder.BIG_ENDIAN)
                .put(bytes, 0, DEFAULT_HEADER_LENGTH);
        int headerLength = buff.getInt(0);
        int clientVersion = buff.getInt(4);
        int seq = buff.getInt(8);
        int cmdId = buff.getInt(12);
        int bodyLength = buff.getInt(16);
        buff.clear();
        return Header.create(headerLength, clientVersion, seq, cmdId, bodyLength);
    }

    public static Header create(int headerLength, int clientVersion, int seq, int cmdId, int bodyLength) {
        Header header = new Header();
        header.setHeaderLength(headerLength);
        header.setClientVersion(clientVersion);
        header.setSeq(seq);
        header.setCmdId(cmdId);
        header.setBodyLength(bodyLength);
        return header;
    }

    public Header copy() {
        return Header.create(this.headerLength, this.clientVersion, this.seq, this.cmdId, this.bodyLength);
    }
}