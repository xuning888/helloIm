package com.github.xuning888.helloim.webapi.exception;


import com.github.xuning888.helloim.api.dto.RestResult;
import com.github.xuning888.helloim.contract.exception.ImException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xuning
 * @date 2026/2/1 12:48
 */
@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(ImException.class)
    public RestResult<Object> handleImException(ImException ex) {
        logger.error("handleImException errMsg: {}", ex.getMessage(), ex);
        RestResult<Object> result = new RestResult<>();
        result.setCode(ex.getCode());
        result.setMsg(ex.getMessage());
        return result;
    }
}
