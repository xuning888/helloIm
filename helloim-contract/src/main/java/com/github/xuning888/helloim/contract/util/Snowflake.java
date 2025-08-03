package com.github.xuning888.helloim.contract.util;

/**
 * @author xuning
 * @date 2025/6/2 02:14
 */
public class Snowflake {

    private final long WORKER_ID;
    public static final long TWEPOCH = 1748801801852L;
    private long sequence = 0L;
    static final int WORKER_ID_BITS = 10;
    static final int MAX_WORKER_ID = -1 ^ -1 << WORKER_ID_BITS;
    static final int SEQUENCE_BITS = 12;

    static final int WORKER_ID_SHIFT = SEQUENCE_BITS;
    static final int TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    static final int SEQUENCE_MASK = -1 ^ -1 << SEQUENCE_BITS;

    private long lastTimestamp = -1L;

    public Snowflake(int workerId) {
        super();
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    MAX_WORKER_ID));
        }
        this.WORKER_ID = workerId;
    }


    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1 & SEQUENCE_MASK;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        }
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String
                    .format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            (this.lastTimestamp - timestamp)));
        }

        this.lastTimestamp = timestamp;
        return timestamp - TWEPOCH << TIMESTAMP_LEFT_SHIFT | WORKER_ID << WORKER_ID_SHIFT
                | this.sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }


    private long timeGen() {
        return System.currentTimeMillis();
    }

    public Long getWorkId() {
        return WORKER_ID;
    }
}