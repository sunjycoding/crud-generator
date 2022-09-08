package com.sunjy.generator.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author created by sunjy on 2022/9/7
 */
@Slf4j
public class ConnectionUtils {

    private volatile static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://172.16.22.136:3306/db_wisdom_control_platform?useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
                String user = "root";
                String password = "d9fv-B8=y54R0";
                connection = DriverManager.getConnection(url, user, password);
                log.info("连接数据库【{}】成功", url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
