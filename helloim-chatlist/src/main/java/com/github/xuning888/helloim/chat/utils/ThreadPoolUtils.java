package com.github.xuning888.helloim.chat.utils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuning
 * @date 2025/10/8 23:25
 */
public class ThreadPoolUtils {

    public static ThreadPoolExecutor requestPool = new ThreadPoolExecutor(200, 200,
            5, TimeUnit.MINUTES, new LinkedBlockingDeque<>(5000));
}
