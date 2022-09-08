package com.sunjy.generator.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author created by sunjy on 2022/9/7
 */
public class DateUtils {

    /**
     * yyyy/MM/dd
     */
    public static final String DATE_PATTERN = "yyyy/MM/dd";


    /**
     * 当前时间【格式化】
     *
     * @return
     */
    public static String now() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate localDate = LocalDate.now();
        return localDate.format(dateTimeFormatter);
    }

}
