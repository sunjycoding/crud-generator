package com.sunjy.generator.service;

import com.sunjy.generator.dto.RequiredInfoDTO;
import com.sunjy.generator.dto.TableColumnDTO;
import com.sunjy.generator.dto.TableDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author created by sunjy on 2022/9/7
 */
public interface TableService {

    /**
     * 列表
     *
     * @param tableName
     * @return
     */
    List<TableDTO> list(String tableName);

    /**
     * 表列名列表
     *
     * @param tableName
     * @return
     */
    List<TableColumnDTO> listTableColumn(String tableName);

    /**
     * 后端代码生成
     *
     * @param tableName
     * @param requiredInfoDTO
     * @param response
     */
    void generateBackendCode(String tableName, RequiredInfoDTO requiredInfoDTO, HttpServletResponse response);

    /**
     * 前端代码生成
     *
     * @param tableName
     * @param requiredInfoDTO
     * @param response
     */
    void generateFrontendCode(String tableName, RequiredInfoDTO requiredInfoDTO, HttpServletResponse response);

}
