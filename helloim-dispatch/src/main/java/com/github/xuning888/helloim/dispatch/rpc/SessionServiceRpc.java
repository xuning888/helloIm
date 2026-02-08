package com.github.xuning888.helloim.dispatch.rpc;


import com.github.xuning888.helloim.api.protobuf.common.v1.ImSession;
import com.github.xuning888.helloim.api.protobuf.session.v1.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xuning
 * @date 2026/2/8 17:40
 */
@Service
public class SessionServiceRpc {

    public static final Logger logger = LoggerFactory.getLogger(SessionServiceRpc.class);

    @DubboReference
    private SessionService sessionService;

    public boolean saveSession(ImSession imSession, String traceId) {
        SaveSessionRequest request = SaveSessionRequest.newBuilder().setImSession(imSession).setTraceId(traceId).build();
        try {
            SaveSessionResponse response = sessionService.saveSession(request);
            return response.getSuccess();
        } catch (Exception ex) {
            logger.error("saveSession, errorMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
            return false;
        }
    }

    public boolean removeSession(ImSession imSession, String traceId) {
        RemoveImSessionRequest request = RemoveImSessionRequest.newBuilder().setImSession(imSession).setTraceId(traceId).build();
        try {
            RemoveImSessionResponse response = sessionService.removeImSession(request);
            return response.getSuccess();
        } catch (Exception ex) {
            logger.error("removeSession, errorMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
            return false;
        }
    }
}
