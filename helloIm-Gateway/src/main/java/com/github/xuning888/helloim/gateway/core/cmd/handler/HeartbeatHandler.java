package com.github.xuning888.helloim.gateway.core.cmd.handler;

import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.CmdHandler;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.session.Session;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import com.github.xuning888.helloim.gateway.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理用户心跳
 *
 * @author xuning
 * @date 2025/10/5 19:20
 */
public class HeartbeatHandler implements CmdHandler {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatHandler.class);

    private final SessionManager sessionManager;

    public HeartbeatHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(CmdEvent cmdEvent) throws Exception {
        String sessionId = cmdEvent.getConn().getId();
        Session session = sessionManager.getSession(sessionId, cmdEvent.getTraceId());
        if (session == null) {
            // 长连接不合法
            CommonUtils.writeEmpty(cmdEvent);
            Conn conn = cmdEvent.getConn();
            // 关闭长连接
            conn.close();
            logger.error("HeartbeatHandler session not found, sessionId: {}, traceId: {}", sessionId, cmdEvent.getTraceId());
            return;
        }
        logger.info("HeartbeatHandler success: sessionId: {}", sessionId);
        // 回复一个空包
        CommonUtils.writeEmpty(cmdEvent);
    }
}
