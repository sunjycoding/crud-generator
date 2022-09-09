package com.sunjy.generator.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

/**
 * @author created by sunjy on 2022/9/8
 */
@Getter
@AllArgsConstructor
public enum BackendFileInfoEnum {

    /**
     * controller
     */
    CONTROLLER(Constants.BACKEND_TEMP_DIR_PATH + "controller" + File.separator,
            "Controller.java", "controller.ftl"),

    /**
     * service
     */
    SERVICE(Constants.BACKEND_TEMP_DIR_PATH + "service" + File.separator,
            "Service.java", "service.ftl"),

    /**
     * serviceImpl
     */
    SERVICE_IMPL(Constants.BACKEND_TEMP_DIR_PATH + "service" + File.separator + "impl" + File.separator,
            "ServiceImpl.java", "serviceImpl.ftl"),

    /**
     * repository
     */
    REPOSITORY(Constants.BACKEND_TEMP_DIR_PATH + "repository" + File.separator,
            "Repository.java", "repository.ftl"),

    /**
     * entity
     */
    ENTITY(Constants.BACKEND_TEMP_DIR_PATH + "entity" + File.separator,
            "Entity.java", "entity.ftl"),

    /**
     * dto
     */
    DTO(Constants.BACKEND_TEMP_DIR_PATH + "dto" + File.separator,
            "DTO.java", "dto.ftl"),

    /**
     * conditionDto
     */
    CONDITION_DTO(Constants.BACKEND_TEMP_DIR_PATH + "dto" + File.separator,
            "ConditionDTO.java", "conditionDTO.ftl"),

    /**
     * convertor
     */
    CONVERTOR(Constants.BACKEND_TEMP_DIR_PATH + "convertor" + File.separator,
            "Convertor.java", "convertor.ftl"),

    ;

    private final String path;

    private final String suffix;

    private final String templateName;

}
