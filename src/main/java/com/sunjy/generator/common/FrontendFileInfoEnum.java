package com.sunjy.generator.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

/**
 * @author created by sunjy on 2022/9/8
 */
@Getter
@AllArgsConstructor
public enum FrontendFileInfoEnum {

    /**
     * apiJs
     */
    API_JS(Constants.FRONTEND_TEMP_DIR_PATH,
            "Api.js", "apiJs.ftl"),

    /**
     * tablePage
     */
    TABLE_PAGE(Constants.FRONTEND_TEMP_DIR_PATH,
            ".vue", "tablePage.ftl"),

    /**
     * addOrUpdatePage
     */
    ADD_OR_UPDATE_PAGE(Constants.FRONTEND_TEMP_DIR_PATH + "components" + File.separator,
            "AddOrUpdate.vue", "addOrUpdatePage.ftl"),

    ;

    private final String path;

    private final String suffix;

    private final String templateName;

}
