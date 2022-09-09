package com.sunjy.generator.controller;

import com.sunjy.generator.common.HttpResult;
import com.sunjy.generator.dto.MysqlInfoDTO;
import com.sunjy.generator.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author created by sunjy on 2022/9/8
 */
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public HttpResult login(HttpServletRequest request, @Validated @RequestBody MysqlInfoDTO mysqlInfoDTO) {
        authService.login(request, mysqlInfoDTO);
        return HttpResult.success();
    }

}
