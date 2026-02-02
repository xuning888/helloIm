package com.github.xuning888.helloim.contract.util;

import com.github.xuning888.helloim.contract.contant.RestResultStatus;
import com.github.xuning888.helloim.api.dto.RestResult;

/**
 * @author xuning
 * @date 2025/10/4 13:54
 */
public class RestResultUtils {

    private static final RestResult<Object> success = new RestResult<>();

    public static RestResult<Object> success() {
        return success;
    }

    public static <T> RestResult<T> success(T data) {
        RestResult<T> result = new RestResult<>();
        result.setData(data);
        return result;
    }

    public static RestResult<Object> serverError() {
        return withStatus(RestResultStatus.SERVER_ERR);
    }

    public static RestResult<Object> invalidParams() {
        return withStatus(RestResultStatus.INVALID);
    }

    public static RestResult<Object> withStatus(RestResultStatus status) {
        RestResult<Object> result = new RestResult<>();
        result.setCode(status.getCode());
        result.setMsg(status.getMessage());
        return result;
    }
}
