package com.github.xuning888.helloim.gateway.core.pipeline;

import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import com.github.xuning888.helloim.gateway.core.handler.DownMsgHandler;
import com.github.xuning888.helloim.gateway.core.handler.MsgHandler;
import com.github.xuning888.helloim.gateway.core.handler.UpMsgHandler;
import com.github.xuning888.helloim.gateway.core.processor.Processor;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务级别的pipeline, 注册业务级别的Handler
 * 项目启动时构建，全局唯一，其中的Handler需要注意线程安全的处理
 * @author xuning
 * @date 2025/8/10 11:17
 */
public class DefaultMsgPipeline implements MsgPipeline {

    private final Map<String,PipelineContext> ctxMap = new HashMap<>();
    private volatile PipelineContext head;
    private volatile PipelineContext tail;
    private Processor processor;

    public DefaultMsgPipeline() {
    }

    @Override
    public void addLast(String name, MsgHandler msgHandler) {
        if (ctxMap.isEmpty()) {
            init(name, msgHandler);
        } else {
            checkDuplicate(name);
            PipelineContext ctx = new PipelineContext(name, msgHandler, this.tail, null);
            this.tail.next = ctx;
            this.tail = ctx;
            ctxMap.put(name, ctx);
        }
    }

    @Override
    public void sendUp(UpCmdEvent upCmdEvent) {
        PipelineContext ctx = getActualUpCtx(this.head);
        if (ctx != null) {
            sendUp(ctx, upCmdEvent);
        }
    }

    @Override
    public void sendDown(DownCmdEvent downCmdEvent) {
        PipelineContext ctx = getActualDownCtx(this.tail);
        if (ctx != null) {
            sendDown(ctx, downCmdEvent);
        }
    }

    @Override
    public Processor processor() {
        return this.processor;
    }

    @Override
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    private void init(String name, MsgHandler msgHandler) {
        PipelineContext ctx = new PipelineContext(name, msgHandler, null, null);
        this.head = this.tail = ctx;
        ctxMap.clear();
        ctxMap.put(name, ctx);
    }

    private void checkDuplicate(String name) {
        PipelineContext pipelineContext = ctxMap.get(name);
        if (pipelineContext != null) {
            throw new IllegalArgumentException("Duplicate handler name: " + name);
        }
    }

    private void sendUp(PipelineContext ctx, UpCmdEvent event) {
        if (ctx == null) {
            return;
        }
        MsgHandler msgHandler = ctx.getMsgHandler();
        if (msgHandler instanceof UpMsgHandler) {
            UpMsgHandler upMsgHandler = (UpMsgHandler) msgHandler;
            upMsgHandler.handleUpEvent(ctx, event);
        }
    }

    private void sendDown(PipelineContext ctx, DownCmdEvent event) {
        if (ctx == null) {
            return;
        }
        MsgHandler msgHandler = ctx.getMsgHandler();
        if (msgHandler instanceof DownMsgHandler) {
            DownMsgHandler downMsgHandler = (DownMsgHandler) msgHandler;
            downMsgHandler.handleDownEvent(ctx, event);
        }
    }

    private PipelineContext getActualUpCtx(PipelineContext ctx) {
        if (ctx == null) {
            return null;
        }
        PipelineContext realCtx = ctx;
        while (!realCtx.canUp) {
            realCtx = realCtx.next;
            if (realCtx == null) {
                return null;
            }
        }
        return realCtx;
    }

    private PipelineContext getActualDownCtx(PipelineContext ctx) {
        if (ctx == null) {
            return null;
        }
        PipelineContext realCtx = ctx;
        while (!realCtx.canDown) {
            realCtx = realCtx.prev;
            if (realCtx == null) {
                return null;
            }
        }
        return realCtx;
    }

    public class PipelineContext {

        /**
         * 处理器
         */
        private final MsgHandler msgHandler;
        /**
         * 处理器的名称
         */
        private final String name;

        /**
         * 前驱结点，下行路径
         */
        private PipelineContext prev;

        /**
         * 后继节点，上行路径
         */
        private PipelineContext next;

        /**
         * 节点是否可以处理上行事件
         */
        private final boolean canUp;

        /**
         * 节点是否可以处理下行事件
         */
        private final boolean canDown;

        public PipelineContext(String name, MsgHandler msgHandler, PipelineContext prev, PipelineContext next) {
            this.name = name;
            this.msgHandler = msgHandler;
            this.canUp = msgHandler instanceof UpMsgHandler;
            this.canDown = msgHandler instanceof DownMsgHandler;
            this.prev = prev;
            this.next = next;
        }

        public String getName() {
            return this.name;
        }

        public MsgHandler getMsgHandler() {
            return this.msgHandler;
        }

        /**
         * 传递上行事件
         */
        public void sendUp(UpCmdEvent upCmdEvent) {
            PipelineContext next = getActualUpCtx(this.next);
            if (next != null) {
                DefaultMsgPipeline.this.sendUp(next, upCmdEvent);
            }
        }

        /**
         * 传递下行事件
         */
        public void sendDown(DownCmdEvent downCmdEvent) {
            PipelineContext prev = getActualDownCtx(this.prev);
            if (prev != null) {
                DefaultMsgPipeline.this.sendDown(prev, downCmdEvent);
            }
        }
    }
}
