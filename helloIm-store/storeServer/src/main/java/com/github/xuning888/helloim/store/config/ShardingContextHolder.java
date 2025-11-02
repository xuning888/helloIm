package com.github.xuning888.helloim.store.config;

/**
 * @author xuning
 * @date 2025/9/21 17:14
 */
public class ShardingContextHolder {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();


    public static void setDatasource(String datasource) {
        threadLocal.set(datasource);
    }

    public static String getDataSource() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}