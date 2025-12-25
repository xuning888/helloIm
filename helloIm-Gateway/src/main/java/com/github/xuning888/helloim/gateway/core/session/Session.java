package com.github.xuning888.helloim.gateway.core.session;

import com.github.xuning888.helloim.contract.contant.GateSessionEvent;
import com.github.xuning888.helloim.contract.meta.GateUser;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 关联用户信息和长连接
 * @author xuning
 * @date 2025/8/10 15:20
 */
public class Session implements Serializable {

    /**
     * session关联的Conn
     */
    private final Conn conn;

    /**
     * session关联的用户
     */
    private final GateUser user;

    /**
     * session的观察者
     */
    private final List<SessionListener> listeners = new ArrayList<>();

    /**
     * session的创建时间
     */
    private final long createTime = System.currentTimeMillis();

    public Session(Conn conn, GateUser user) {
        this.conn = conn;
        this.user = user;
    }

    public String getId() {
        return this.conn.getId();
    }

    public GateUser getUser() {
        return user;
    }

    public Conn getConn() {
        return this.conn;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void close(GateSessionEvent sessionEvent) {
        // 关闭长连接
        conn.close();
        // 通知观察者
        for (SessionListener listener : listeners) {
            listener.notifyEvent(this, sessionEvent);
        }
    }

    public boolean isOk() {
        return conn != null && conn.isOk();
    }

    public void addListener(SessionListener sessionListener) {
        this.listeners.add(sessionListener);
    }


    public MsgPipeline msgPipeline() {
        return this.conn.getMsgPipeline();
    }

    @Override
    public String toString() {
        return conn.getId() + "_" + user + "_" + createTime;
    }
}