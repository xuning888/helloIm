package com.github.xuning888.helloim.contract.util;


import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * 即包含GenericJackson2JsonRedisSerializer的功能, 但是不对字符串进行二次序列化, 二次序列化后lua脚本的cjson无法处理数据。
 * @author xuning
 * @date 2026/2/7 21:42
 */
public class SimpleRedisSerializer extends GenericJackson2JsonRedisSerializer {

    static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public byte[] serialize(@Nullable Object source) throws SerializationException {
        if (source == null) {
            return EMPTY_ARRAY;
        }
        // 如果发现是String, 就不再序列化
        if (source instanceof String) {
            return ((String) source).getBytes(StandardCharsets.UTF_8);
        }
        return super.serialize(source);
    }

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        // 如果无法按照jackson的格式反序列化, 就尝试按照String返回, 由业务处理。
        try {
            return super.deserialize(source);
        } catch (Exception e) {
            try {
                return new String(source, StandardCharsets.UTF_8);
            } catch (Exception ex) {
                throw new SerializationException("Deserialization failed and cannot convert to string", ex);
            }
        }
    }
}
