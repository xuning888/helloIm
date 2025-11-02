package com.github.xuning888.helloim.message.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuning
 * @date 2025/8/23 0:08
 */
public class ThreadPoolUtils {

    public static ThreadPoolExecutor requestPool = new ThreadPoolExecutor(200, 200, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(5000));
}