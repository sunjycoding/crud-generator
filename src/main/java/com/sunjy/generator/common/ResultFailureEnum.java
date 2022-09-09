package com.sunjy.generator.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Created by sunjy on 2022/9/9
 */
@Getter
@AllArgsConstructor
public enum ResultFailureEnum {

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(1,"连接数据库失败"),

    ;

    private final Integer code;

    private final String message;


}
