package com.github.xuning888.helloim.gateway.core.session;

import com.github.xuning888.helloim.contract.api.request.LogoutRequest;
import com.github.xuning888.helloim.contract.api.response.LogoutResponse;
import com.github.xuning888.helloim.contract.contant.GateSessionEvent;
import com.github.xuning888.helloim.contract.contant.GateSessionState;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.gateway.adapter.UpMsgServiceAdapter;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.conn.event.ConnStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author xuning
 * @date 2025/8/10 15:24
 */
public class DefaultSessionManager implements SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSessionManager.class);

    private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private final Map<GateUser, Session> userSessionMap = new ConcurrentHashMap<>();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();

    private final UpMsgServiceAdapter upMsgServiceAdapter;

    public DefaultSessionManager(UpMsgServiceAdapter upMsgServiceAdapter) {
        this.upMsgServiceAdapter = upMsgServiceAdapter;
        registerTask();
    }


    @Override
    public void putSession(Session session, String traceId) {
        Session oldSession = sessionMap.get(session.getId());
        if (oldSession != null) {
            logger.info("session重复auth, 需要检查是同一个用户的session, sessionId: {}", session.getId());
            checkAndDeleteOldDSession(session, oldSession, traceId);
        }
        sessionMap.put(session.getId(), session);
        userSessionMap.put(session.getUser(), session);
    }

    /**
     * 关闭session, 传递关闭原因
     * @param connStateEvent 长连接状态
     */
    @Override
    public void changeState(ConnStateEvent connStateEvent) {
        Conn conn = connStateEvent.getConn();
        String traceId = connStateEvent.getTraceId();
        String sessionId = connStateEvent.getConn().getId();
        ConnStateEvent.State state = connStateEvent.getState();
        switch (state) {
            case OPEN:
                logger.info("连接建立, sessionId: {}, traceId: {}", sessionId, traceId);
                break;
            case CLOSE:
                closeSession(sessionId, GateSessionState.CLOSE, conn, traceId);
                break;
            case TIMEOUT:
                closeSession(sessionId, GateSessionState.TIMEOUT, conn, traceId);
                break;
            case ERROR:
                closeSession(sessionId, GateSessionState.ERROR, conn, traceId);
                break;
            default:
                logger.error("changeState, 不能在这处理的关闭事件, sessionId: {}, state: {}, traceId: {}", sessionId, state, traceId);
                break;
        }
    }

    @Override
    public Session getSession(String sessionId, String traceId) {
        Session session = sessionMap.get(sessionId);
        if (session == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("getSession is null, sessionId: {}, traceId: {}", sessionId, traceId);
            }
        }
        return session;
    }

    @Override
    public Session getSessionByUser(GateUser user, String traceId) {
        Session session = userSessionMap.get(user);
        if (session == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("getSessionByUser, user: {}, traceId: {}", user, traceId);
            }
        }
        return session;
    }

    @Override
    public void logout(String sessionId, String traceId) {
        Session session = sessionMap.get(sessionId);
        if (session != null) {
            closeSession(sessionId, GateSessionState.LOGOUT, session.getConn(), traceId);
        }
    }

    private void closeSession(String sessionId, GateSessionState sessionState, Conn conn, String traceId) {
        Session session = sessionMap.remove(sessionId);
        if (session != null) {
            logger.info("closeSession, session: {}, state: {}, traceId: {}", session, sessionState, traceId);
            asyncCloseSession(session, new GateSessionEvent(sessionState, session.getUser(), traceId));
            GateUser user = session.getUser();
            userSessionMap.remove(user);
        } else {
            logger.info("closeSession, session is null, sessionId: {}, traceId: {}", sessionId, traceId);
            // 确保长连接也被关闭
            if (conn != null) {
                conn.close();
            }
        }
    }

    private void asyncCloseSession(Session session, GateSessionEvent gateSessionEvent) {
        executorService.execute(new CloseSessionTask(session, gateSessionEvent));
    }


    private void checkAndDeleteOldDSession(Session session, Session oldSession, String traceId) {
        if (!Objects.equals(session.getUser(), oldSession.getUser())) {
            try {
                LogoutRequest logoutRequest = new LogoutRequest();
                logoutRequest.setGateUser(oldSession.getUser());
                logoutRequest.setTraceId(traceId);
                logoutRequest.setSessionEvent(new GateSessionEvent(GateSessionState.LOGOUT, oldSession.getUser(), traceId));
                LogoutResponse logoutResponse = upMsgServiceAdapter.upMsgService().logout(logoutRequest);
                logger.info("logoutResponse: {}, traceId: {}", logoutResponse, traceId);
            } catch (Exception ex) {
                logger.error("checkAndDeleteOldDSession logout error, traceId: {}", traceId, ex);
            }
        }
    }

    private void registerTask() {
        // 注册定时任务, 检查并清理关闭的session
        scheduled.scheduleWithFixedDelay(new CheckSessionStateTask(), 10, 10, TimeUnit.SECONDS);
    }


    private static class CloseSessionTask implements Runnable {

        private final Session session;
        public final GateSessionEvent sessionEvent;

        public CloseSessionTask(Session session, GateSessionEvent sessionEvent) {
            this.session = session;
            this.sessionEvent = sessionEvent;
        }

        @Override
        public void run() {
            session.close(sessionEvent);
        }
    }

    private class CheckSessionStateTask implements Runnable {

        @Override
        public void run() {
            String traceId = UUID.randomUUID().toString();
            Map<String, Session> copy = new HashMap<>(sessionMap);
            logger.info("session size: {}, traceId: {}", copy.size(), traceId);
            Set<Map.Entry<String, Session>> entries = copy.entrySet();
            for (Map.Entry<String, Session> entry : entries) {
                String sessionId = entry.getKey();
                Session session = entry.getValue();
                if (session.getConn().isOk()) {
                    continue;
                }
                closeSession(sessionId, GateSessionState.CLOSE, session.getConn(), traceId);
            }
        }
    }
}