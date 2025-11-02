package com.github.xuning888.helloim.store.utils;

/**
 * @author xuning
 * @date 2025/9/21 17:18
 */
public class ShardingUtils {

    public static String shardingFor4(Long shardingValue) {
        long index = shardingValue % 4;
        return "datasource" + index;
    }
}