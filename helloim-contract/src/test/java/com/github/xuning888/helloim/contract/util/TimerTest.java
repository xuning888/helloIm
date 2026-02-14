package com.github.xuning888.helloim.contract.util;


import com.github.xuning888.helloim.contract.util.timer.SystemTimer;
import com.github.xuning888.helloim.contract.util.timer.SystemTimerReaper;
import com.github.xuning888.helloim.contract.util.timer.TimerTask;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author xuning
 * @date 2026/2/14 17:13
 */
public class TimerTest {

    @Test
    public void test() throws Exception {
        SystemTimerReaper timer = new SystemTimerReaper("aaaa-b", new SystemTimer("aaa"));
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            timer.add(new TimerTask(1000) {
                @Override
                public void run() {
                    System.out.println("hello world");
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
        } finally {
            timer.close();
        }
    }
}
