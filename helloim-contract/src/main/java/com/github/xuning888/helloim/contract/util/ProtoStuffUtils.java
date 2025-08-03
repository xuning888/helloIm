package com.github.xuning888.helloim.contract.util;


import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author xuning
 * @date 2025/6/2 19:25
 */
public class ProtoStuffUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProtoStuffUtils.class);

    /**
     * 使用 ProtoStuff 序列化
     *
     * @param obj obj
     * @param <T> Object
     * @return bytes
     */
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new NullPointerException("ProtoStuffUtils.serialize 序列化对象为null");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024);
        byte[] bytes = null;
        try {
            bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception ex) {
            logger.error("ProtoStuffUtils.serialize 序列化对象, ({}) -> {}", obj.getClass(), obj, ex);
            throw new RuntimeException("ProtoStuffUtils.serialize 序列化对象, (" + obj.getClass() + ") -> " + obj);
        } finally {
            buffer.clear();
        }
        return bytes;
    }

    /**
     * 使用 protoStuff 反序列化出对象
     */
    public static <T> T deserialize(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException("ProtoStuffUtils.deserialize 反序列化对象发生异常, byte序列为空!");
        }
        T instance = null;
        try {
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException("ProtoStuffUtils.deserialize 反序列化过程中依据类型创建对象失败!", e);
        }
        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
        return instance;
    }
}