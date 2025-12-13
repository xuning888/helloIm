package com.github.xuning888.helloim.gateway.utils;


import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author xuning
 * @date 2025/12/12 20:34
 */
public class TimeWheelUtils {

    private static final HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();

    public static Timeout addTask(TimerTask timerTask, long delay, TimeUnit timeUnit) {
        return hashedWheelTimer.newTimeout(timerTask, delay, timeUnit);
    }
}
