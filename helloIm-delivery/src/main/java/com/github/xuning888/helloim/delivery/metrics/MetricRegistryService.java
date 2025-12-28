package com.github.xuning888.helloim.delivery.metrics;


import com.github.xuning888.helloim.contract.contant.MetricsConstant;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xuning
 * @date 2025/12/28 18:41
 */
@Component
public class MetricRegistryService {

    private final PrometheusMeterRegistry registry;

    public MetricRegistryService(PrometheusMeterRegistry registry) {
        this.registry = registry;
    }

    /**
     * 记录消息在IM消息通道中的延迟
     * duration = sendTimestamp - 调用完gateway的时间
     */
    public void recordLatency(MsgContext msgContext) {
        long timestamp = msgContext.getTimestamp();
        Timer timer = getTimer(msgContext);
        long duration = System.currentTimeMillis() - timestamp;
        timer.record(duration, TimeUnit.MILLISECONDS);
    }

    private Timer getTimer(MsgContext msgContext) {
        return Timer.builder(MetricsConstant.MESSAGE_LATENCY) // 指标
                .tags(getTags(msgContext)) // 标签
                .publishPercentiles(0.5, 0.95, 0.99) // tp50, tp95, tp99
                .register(registry);
    }

    private String[] getTags(MsgContext msgContext) {
        Frame frame = msgContext.getFrame();
        Header header = frame.getHeader();
        int cmdId = header.getCmdId();
        String[] tags = new String[2];
        tags[0] = "cmdId";
        tags[1] = String.valueOf(cmdId);
        return tags;
    }
}
