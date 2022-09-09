package com.sunjy.generator.service;

import com.sunjy.generator.dto.MysqlInfoDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author created by sunjy on 2022/9/8
 */
public interface AuthService {

    /**
     * login
     *
     * @param request
     * @param mysqlInfoDTO
     */
    void login(HttpServletRequest request, MysqlInfoDTO mysqlInfoDTO);

    /**
     * logout
     */
    void logout();

}
