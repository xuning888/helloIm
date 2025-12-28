package com.github.xuning888.helloim.gateway.core.handler.impl;


import com.github.xuning888.helloim.contract.contant.MetricsConstant;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import com.github.xuning888.helloim.gateway.core.handler.DownMsgHandler;
import com.github.xuning888.helloim.gateway.core.handler.UpMsgHandler;
import com.github.xuning888.helloim.gateway.core.pipeline.DefaultMsgPipeline;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 指标收集
 *
 * @author xuning
 * @date 2025/12/28 17:21
 */
public class PrometheusHandler implements UpMsgHandler, DownMsgHandler {

    private static final Logger logger = LoggerFactory.getLogger(PrometheusHandler.class);

    private final PrometheusMeterRegistry registry;

    public PrometheusHandler(PrometheusMeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void handleUpEvent(DefaultMsgPipeline.PipelineContext ctx, UpCmdEvent event) {
        try {
            processCount(MetricsConstant.UP_REQUEST_COUNT_METRICS, getTags(event));
            processByteCount(MetricsConstant.UP_REQUEST_BYTE_COUNT_METRICS, getTags(event), bytesSize(event));
        } catch (Exception ex) {
            logger.error("handleUpEvent, traceId:{}", event.getTraceId(), ex);
        }
        ctx.sendUp(event);
    }

    @Override
    public void handleDownEvent(DefaultMsgPipeline.PipelineContext ctx, DownCmdEvent event) {
        try {
            processCount(MetricsConstant.DOWN_REQUEST_COUNT_METRICS, getTags(event));
            processByteCount(MetricsConstant.DOWN_REQUEST_BYTE_COUNT_METRICS, getTags(event), bytesSize(event));
        } catch (Exception ex) {
            logger.error("handleDownEvent, traceId:{}", event.getTraceId(), ex);
        }
        ctx.sendDown(event);
    }

    private String[] getTags(CmdEvent cmdEvent) {
        Frame frame = cmdEvent.getFrame();
        Header header = frame.getHeader();
        int cmdId = header.getCmdId();
        String[] tags = new String[2];
        tags[0] = "cmdId";
        tags[1] = String.valueOf(cmdId);
        return tags;
    }

    private void processCount(String metricsName, String[] tags) {
        Counter.builder(metricsName).tags(tags).register(registry).increment();
    }

    private void processByteCount(String metricsName, String[] tags, double v) {
        Counter.builder(metricsName).tags(tags).baseUnit("bytes").register(registry).increment(v);
    }

    private double bytesSize(CmdEvent cmdEvent) {
        Frame frame = cmdEvent.getFrame();
        Header header = frame.getHeader();
        byte headerLength = header.getHeaderLength();
        int bodySize = frame.getBody() == null ? 0 : frame.getBody().length;
        return bodySize + (int) headerLength;
    }
}
