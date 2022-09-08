package com.sunjy.generator.controller;

import com.sunjy.generator.common.HttpResult;
import com.sunjy.generator.dto.RequiredInfoDTO;
import com.sunjy.generator.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author created by sunjy on 2022/9/7
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("table")
public class TableController {

    private final TableService tableService;

    @GetMapping
    public HttpResult list(@RequestParam(required = false) String tableName) {
        return HttpResult.success(tableService.list(tableName));
    }

    @GetMapping("column")
    public HttpResult listTableColumn(@RequestParam String tableName) {
        return HttpResult.success(tableService.listTableColumn(tableName));
    }

    @PostMapping("backend")
    public HttpResult generateBackendCode(@RequestParam String tableName, @RequestBody RequiredInfoDTO requiredInfoDTO, HttpServletResponse response) {
        tableService.generateBackendCode(tableName, requiredInfoDTO, response);
        return HttpResult.success();
    }

    @PostMapping("frontend")
    public HttpResult generateFrontendCode(@RequestParam String tableName, @RequestBody RequiredInfoDTO requiredInfoDTO, HttpServletResponse response) {
        tableService.generateFrontendCode(tableName, requiredInfoDTO, response);
        return HttpResult.success();
    }

}
