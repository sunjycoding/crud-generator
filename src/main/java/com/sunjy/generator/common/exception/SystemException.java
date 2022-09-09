package com.sunjy.generator.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author created by sunjy on 2022/9/9
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemException extends RuntimeException {

    private Integer code;

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(String message) {
        super(message);
    }

}
