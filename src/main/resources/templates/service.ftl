package com.hnbhbt.${moduleName}.service;

import com.hnbhbt.common.PageParams;
import com.hnbhbt.common.Pagination;
import com.hnbhbt.${moduleName}.dto.${tableNameFirstLetterUppercase}ConditionDTO;
import com.hnbhbt.${moduleName}.dto.${tableNameFirstLetterUppercase}DTO;

import java.util.List;

/**
 * @author created by ${author} on ${date}
 */
public interface ${tableNameFirstLetterUppercase}Service {

    /**
     * 分页列表
     *
     * @param pageParams
     * @param ${tableNameFirstLetterLowercase}ConditionDTO
     * @return
     */
    Pagination<${tableNameFirstLetterUppercase}DTO> page(PageParams pageParams, ${tableNameFirstLetterUppercase}ConditionDTO ${tableNameFirstLetterLowercase}ConditionDTO);

    /**
     * 列表
     *
     * @return
     */
    List<${tableNameFirstLetterUppercase}DTO> list();

    /**
     * 查询
     *
     * @param id
     * @return
     */
    ${tableNameFirstLetterUppercase}DTO get(String id);

    /**
     * 查询
     *
     * @param paramKey
     * @return
     */
    ${tableNameFirstLetterUppercase}DTO getByParamKey(String paramKey);

    /**
     * 新增
     *
     * @param ${tableNameFirstLetterLowercase}DTO
     */
    void create(${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}DTO);

    /**
     * 修改
     *
     * @param ${tableNameFirstLetterLowercase}DTO
     */
    void update(${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}DTO);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 批量删除
     *
     * @param idList
     */
    void delete(List<String> idList);

}
