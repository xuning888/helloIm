package com.github.xuning888.helloim.contract.frame;

import java.io.Serializable;
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

    @Override
    public String toString() {
        return "Frame{" +
                "header=" + header +
                ", bodySize=" + (body == null ? 0 : body.length) + // 小心点
                '}';
    }


}