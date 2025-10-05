package com.github.xuning888.helloim.gateway.utils;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.gateway.core.cmd.CmdEvent;

/**
 * @author xuning
 * @date 2025/10/5 19:25
 */
public class CommonUtils {

    public static void writeEmpty(CmdEvent cmdEvent) {
        Frame frame = cmdEvent.getFrame();
        Header header = frame.getHeader();
        Header copy = header.copy();
        copy.setBodyLength(0);
        Frame response = new Frame(copy, new byte[0]);
        cmdEvent.getConn().write(response, cmdEvent.getTraceId());
    }
}
