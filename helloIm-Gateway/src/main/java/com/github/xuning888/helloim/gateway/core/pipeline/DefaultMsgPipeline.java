package com.github.xuning888.helloim.gateway.core.pipeline;

import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import com.github.xuning888.helloim.gateway.core.handler.DownMsgHandler;
import com.github.xuning888.helloim.gateway.core.handler.MsgHandler;
import com.github.xuning888.helloim.gateway.core.handler.UpMsgHandler;

/**
 * @author xuning
 * @date 2025/8/10 11:17
 */
public class DefaultMsgPipeline implements MsgPipeline {


    @Override
    public void addLast(String name, MsgHandler msgHandler) {
    }

    @Override
    public void sendUp(UpCmdEvent upCmdEvent) {
    }

    @Override
    public void sendDown(DownCmdEvent downCmdEvent) {
    }


    private void sendUp(PipelineContext ctx, UpCmdEvent event) {

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

        private final MsgHandler msgHandler;
        private final String name;
        private DefaultMsgPipeline msgPipeline;
        private PipelineContext prev;
        private PipelineContext next;
        private final boolean canUp;
        private final boolean canDown;

        public PipelineContext(String name, MsgHandler msgHandler) {
            this.name = name;
            this.msgHandler = msgHandler;
            this.canUp = msgHandler instanceof UpMsgHandler;
            this.canDown = msgHandler instanceof DownMsgHandler;
        }

        public String getName() {
            return this.name;
        }

        public MsgHandler getMsgHandler() {
            return this.msgHandler;
        }

        public void sendUp(UpCmdEvent upCmdEvent) {
            PipelineContext next = getActualUpCtx(this.next);
            if (next != null) {
                DefaultMsgPipeline.this.sendUp(next, upCmdEvent);
            }
        }

        public void sendDown(DownCmdEvent downCmdEvent) {
            PipelineContext prev = getActualDownCtx(this.prev);
            if (prev != null) {

            }
        }
    }
}
