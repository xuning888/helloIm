package com.github.xuning888.helloim.contract.util.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper for Thread that sets things up nicely
 */
public class KafkaThread extends Thread {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public static KafkaThread daemon(final String name, Runnable runnable) {
        return new KafkaThread(name, runnable, true);
    }

    public static KafkaThread nonDaemon(final String name, Runnable runnable) {
        return new KafkaThread(name, runnable, false);
    }

    public KafkaThread(final String name, boolean daemon) {
        super(name);
        configureThread(name, daemon);
    }

    public KafkaThread(final String name, Runnable runnable, boolean daemon) {
        super(runnable, name);
        configureThread(name, daemon);
    }

    private void configureThread(final String name, boolean daemon) {
        setDaemon(daemon);
        setUncaughtExceptionHandler((t, e) -> log.error("Uncaught exception in thread '{}':", name, e));
    }

}
