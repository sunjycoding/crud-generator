package com.sunjy.generator.common;

import com.sunjy.generator.common.exception.SystemException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author created by sunjy on 2022/9/6
 */
@Data
@AllArgsConstructor
public class HttpResult {

    private Integer code;
    private String msg;
    private Object data;

    public HttpResult() {
        this.code = 0;
        this.msg = "success";
    }

    public static HttpResult success() {
        return new HttpResult();
    }

    public static HttpResult success(Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.setData(data);
        return httpResult;
    }

    public static HttpResult failure(Exception e) {
        HttpResult httpResult = new HttpResult();
        int code = 1;
        String msg = ResultFailureEnum.DATABASE_CONNECTED_ERROR.getMessage();
        if (e instanceof SystemException) {
            if (((SystemException) e).getCode() != null) {
                code = ((SystemException) e).getCode();
            }
            msg = e.getMessage();
        } else if (e.getCause() instanceof SystemException cause) {
            if (cause.getCode() != null) {
                code = cause.getCode();
            }
            msg = e.getMessage();
        }
        httpResult.setCode(code);
        httpResult.setMsg(msg);
        return httpResult;
    }

    public static HttpResult failure(String msg) {
        HttpResult httpResult = new HttpResult();
        int code = 1;
        httpResult.setCode(code);
        httpResult.setMsg(msg);
        return httpResult;
    }

}
