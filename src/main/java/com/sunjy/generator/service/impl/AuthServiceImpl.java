package com.sunjy.generator.service.impl;

import com.sunjy.generator.common.utils.ConnectionUtils;
import com.sunjy.generator.dto.MysqlInfoDTO;
import com.sunjy.generator.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author created by sunjy on 2022/9/8
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void login(HttpServletRequest request, MysqlInfoDTO mysqlInfoDTO) {
        String prefix = "jdbc:mysql://";
        String ip = mysqlInfoDTO.getIp();
        String port = mysqlInfoDTO.getPort();
        String dbName = mysqlInfoDTO.getDbName();
        String url = prefix + ip + ":" + port + "/" + dbName;
        String user = mysqlInfoDTO.getUser();
        String password = mysqlInfoDTO.getPassword();
        log.info("等待连接, 数据库地址【{}】", url);
        ConnectionUtils.connect(url, user, password);
        request.getSession().setAttribute(ConnectionUtils.DB_NAME_KEY, dbName);
    }

    @Override
    public void logout() {
        ConnectionUtils.close();
    }

}
