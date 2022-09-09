package com.sunjy.generator.config;

import com.sunjy.generator.common.HttpResult;
import com.sunjy.generator.common.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 *
 * @author created by sunjy on 2022/9/9
 */
@RestControllerAdvice
public class SystemExceptionHandler {

    @ExceptionHandler({Exception.class, SystemException.class})
    public HttpResult exceptionHandler(Exception e) {
        e.printStackTrace();
        return HttpResult.failure(e);
    }

}
