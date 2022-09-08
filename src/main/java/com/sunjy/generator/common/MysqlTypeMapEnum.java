package com.sunjy.generator.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Mysql类型对应Java类型
 *
 * @author created by sunjy on 2022/9/8
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum MysqlTypeMapEnum {
    /**
     * CHAR
     */
    CHAR("CHAR", "String"),

    /**
     * VARCHAR
     */
    VARCHAR("VARCHAR", "String"),

    /**
     * TEXT
     */
    TEXT("TEXT", "String"),

    /**
     * INT
     */
    INT("INT", "Integer"),

    /**
     * BIGINT
     */
    BIGINT("BIGINT", "Integer"),

    /**
     * FLOAT
     */
    FLOAT("FLOAT", "Float"),

    /**
     * DOUBLE
     */
    DOUBLE("DOUBLE", "Double"),

    /**
     * TIMESTAMP
     */
    TIMESTAMP("TIMESTAMP", "Long"),

    /**
     * TINYINT
     */
    TINYINT("TINYINT", "Boolean"),

    /**
     * BIT
     */
    BIT("BIT", "Boolean"),

    /**
     * DATE
     */
    DATE("DATE", "Date"),

    /**
     * DATETIME
     */
    DATETIME("DATETIME", "Date"),

    /**
     * LONGBLOB
     */
    LONGBLOB("LONGBLOB", "Byte"),

    ;

    private final String mysqlType;

    private final String javaType;

    public static MysqlTypeMapEnum getByMysqlType(String mysqlType) {
        MysqlTypeMapEnum[] mysqlTypeMapEnums = MysqlTypeMapEnum.values();
        for (MysqlTypeMapEnum mysqlTypeMapEnum : mysqlTypeMapEnums) {
            if (mysqlTypeMapEnum.getMysqlType().equals(mysqlType)) {
                return mysqlTypeMapEnum;
            }
        }
        log.error("Mysql类型【{}】", mysqlType);
        throw new RuntimeException("找不到对应类型");
    }

    public static String getJavaTypeByMysqlType(String mysqlType) {
        return getByMysqlType(mysqlType).getJavaType();
    }
}
