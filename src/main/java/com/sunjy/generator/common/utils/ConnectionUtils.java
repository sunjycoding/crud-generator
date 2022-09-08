package com.sunjy.generator.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author created by sunjy on 2022/9/7
 */
@Slf4j
@Component
public class ConnectionUtils {

    public static String DB_NAME_KEY = "dbName";

    public static String CONNECTION_KEY = "connection";

    private static HttpServletRequest REQUEST;

    @Autowired
    public ConnectionUtils(HttpServletRequest request) {
        ConnectionUtils.REQUEST = request;
    }


    public static void connect(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            log.info("连接数据库【{}】成功", url);
            REQUEST.getSession().setAttribute(CONNECTION_KEY, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = (Connection) REQUEST.getSession().getAttribute(CONNECTION_KEY);
        if (connection != null) {
            return connection;
        }
        throw new RuntimeException("No Connection Found");
    }

    public static String getDbName() {
        return (String) REQUEST.getSession().getAttribute(DB_NAME_KEY);
    }

    public static void close() {
        Connection connection = (Connection) REQUEST.getSession().getAttribute(CONNECTION_KEY);
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
