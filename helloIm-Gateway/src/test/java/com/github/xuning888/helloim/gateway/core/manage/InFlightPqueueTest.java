package com.github.xuning888.helloim.gateway.core.manage;


import com.alibaba.fastjson2.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author xuning
 * @date 2026/2/12 18:07
 */
public class InFlightPqueueTest {

    @Test
    public void test() {
        PriorityQueue<RetryMessage> standardPq = new PriorityQueue<>(Comparator.comparing(RetryMessage::getPri));
        InFlightPqueue inFlightPqueue = new InFlightPqueue(16);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            RetryMessage retryMessage = new RetryMessage();
            retryMessage.setPri(i);
            inFlightPqueue.push(retryMessage);
            standardPq.offer(retryMessage);
        }
        while (inFlightPqueue.size() > 0) {
            RetryMessage pop = inFlightPqueue.pop();
            RetryMessage expected = standardPq.poll();
            Assert.assertEquals(expected, pop);
        }
    }

    @Test
    public void test_random_remove() {
        InFlightPqueue inFlightPqueue = new InFlightPqueue(16);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            RetryMessage retryMessage = new RetryMessage();
            retryMessage.setPri(i);
            inFlightPqueue.push(retryMessage);
        }
        Random random = new Random();
        int cnt = 0;
        while (inFlightPqueue.size() > 0) {
            int i = random.nextInt(n);
            RetryMessage remove = inFlightPqueue.remove(i);
            if (remove == null) {
                continue;
            }
            System.out.println(JSON.toJSONString(remove));
            cnt++;
        }
        System.out.println(cnt);
    }
}
