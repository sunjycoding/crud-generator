package com.sunjy.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author created by sunjy on 2022/9/7
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RequiredInfoDTO {

    /**
     * 作者
     */
    private String author;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * Controller 路由路径
     */
    private String routerPath;

    /**
     * Controller 接口描述
     */
    private String apiDescription;

    /**
     * DTO 描述
     */
    private String dtoDescription;

}
