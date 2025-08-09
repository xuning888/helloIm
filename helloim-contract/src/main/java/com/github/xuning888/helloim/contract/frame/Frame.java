package com.github.xuning888.helloim.contract.frame;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author xuning
 * @date 2025/8/2 16:11
 */
public class Frame implements Serializable {

    private Header header;

    private byte[] body;

    public Frame() {}

    public Frame(Header header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer
                .allocate(Header.DEFAULT_HEADER_LENGTH + (body == null ? 0 : body.length))
                .order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(header.getHeaderLength());
        buffer.putInt(header.getClientVersion());
        buffer.putInt(header.getSeq());
        buffer.putInt(header.getCmdId());
        buffer.putInt(header.getBodyLength());
        if (body != null && body.length != 0) {
            buffer.put(body);
        }
        return buffer.array();
    }

    public static Frame create(ByteBuffer buffer) {
        byte[] array = buffer.array();
        return create(array);
    }

    public static Frame create(byte[] bytes) {
        if (bytes == null || bytes.length < Header.DEFAULT_HEADER_LENGTH) {
            throw new IllegalArgumentException("bytes的长度小于20");
        }
        Header header = Header.create(Arrays.copyOf(bytes, Header.DEFAULT_HEADER_LENGTH));
        int bodyLength = header.getBodyLength();
        if (bodyLength != (bytes.length - Header.DEFAULT_HEADER_LENGTH)) {
            throw new IllegalArgumentException("bytes不包含完整消息体");
        }
        byte[] body = new byte[bodyLength];
        System.arraycopy(bytes, Header.DEFAULT_HEADER_LENGTH, body, 0, bodyLength);
        return new Frame(header, body);
    }

    @Override
    public String toString() {
        return "Frame{" +
                "header=" + header +
                ", bodySize=" + (body == null ? 0 : body.length) + // 小心点
                '}';
    }


}