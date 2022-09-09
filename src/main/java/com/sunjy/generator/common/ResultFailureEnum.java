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
     * 数据库连接失败
     */
    DATABASE_CONNECTED_ERROR(1,"连接数据库失败, 请重新连接"),

    ;

    private final Integer code;

    private final String message;


}
