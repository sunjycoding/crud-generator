package ${basePackage}.${moduleName}.convertor;

import ${basePackage}.common.utils.TranslateUtils;
import ${basePackage}.${moduleName}.dto.${tableNameFirstLetterUppercase}DTO;
import ${basePackage}.${moduleName}.entity.${tableNameFirstLetterUppercase}Entity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by ${author} on ${date}
 */
public class ${tableNameFirstLetterUppercase}Convertor {

    /**
     * ${tableNameFirstLetterLowercase}Entity -> ${tableNameFirstLetterLowercase}DTO
     *
     * @param ${tableNameFirstLetterLowercase}Entity
     * @return
     */
    public static ${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}Entity2Dto(${tableNameFirstLetterUppercase}Entity ${tableNameFirstLetterLowercase}Entity) {
        if (${tableNameFirstLetterLowercase}Entity != null) {
            ${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}DTO = new ${tableNameFirstLetterUppercase}DTO();
            BeanUtils.copyProperties(${tableNameFirstLetterLowercase}Entity, ${tableNameFirstLetterLowercase}DTO);
            ${tableNameFirstLetterLowercase}DTO.setTypeValue(TranslateUtils.translateDictDetailValueByDictDetailCode(${tableNameFirstLetterLowercase}DTO.getTypeCode()));
            return ${tableNameFirstLetterLowercase}DTO;
        }
        return null;
    }

    /**
     * ${tableNameFirstLetterLowercase}EntityList -> ${tableNameFirstLetterLowercase}DTOList
     *
     * @param ${tableNameFirstLetterLowercase}EntityList
     * @return
     */
    public static List<${tableNameFirstLetterUppercase}DTO> ${tableNameFirstLetterLowercase}EntityList2DtoList(List<${tableNameFirstLetterUppercase}Entity> ${tableNameFirstLetterLowercase}EntityList) {
        if (CollectionUtils.isNotEmpty(${tableNameFirstLetterLowercase}EntityList)) {
            List<${tableNameFirstLetterUppercase}DTO> ${tableNameFirstLetterLowercase}DTOList = new ArrayList<>();
            ${tableNameFirstLetterLowercase}EntityList.forEach(${tableNameFirstLetterLowercase}Entity -> {
                ${tableNameFirstLetterLowercase}DTOList.add(${tableNameFirstLetterLowercase}Entity2Dto(${tableNameFirstLetterLowercase}Entity));
            });
            return ${tableNameFirstLetterLowercase}DTOList;
        }
        return null;
    }

    /**
     * ${tableNameFirstLetterLowercase}DTO -> ${tableNameFirstLetterLowercase}Entity
     *
     * @param ${tableNameFirstLetterLowercase}DTO
     * @return
     */
    public static ${tableNameFirstLetterUppercase}Entity ${tableNameFirstLetterLowercase}Dto2Entity(${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}DTO) {
        if (${tableNameFirstLetterLowercase}DTO != null) {
            ${tableNameFirstLetterUppercase}Entity ${tableNameFirstLetterLowercase}Entity = new ${tableNameFirstLetterUppercase}Entity();
            BeanUtils.copyProperties(${tableNameFirstLetterLowercase}DTO, ${tableNameFirstLetterLowercase}Entity);
            return ${tableNameFirstLetterLowercase}Entity;
        }
        return null;
    }

    /**
     * ${tableNameFirstLetterLowercase}DTOList -> ${tableNameFirstLetterLowercase}EntityList
     *
     * @param ${tableNameFirstLetterLowercase}DTOList
     * @return
     */
    public static List<${tableNameFirstLetterUppercase}Entity> ${tableNameFirstLetterLowercase}DtoList2EntityList(List<${tableNameFirstLetterUppercase}DTO> ${tableNameFirstLetterLowercase}DTOList) {
        if (CollectionUtils.isNotEmpty(${tableNameFirstLetterLowercase}DTOList)) {
            List<${tableNameFirstLetterUppercase}Entity> ${tableNameFirstLetterLowercase}EntityList = new ArrayList<>();
            ${tableNameFirstLetterLowercase}DTOList.forEach(${tableNameFirstLetterLowercase}DTO -> {
                ${tableNameFirstLetterLowercase}EntityList.add(${tableNameFirstLetterLowercase}Dto2Entity(${tableNameFirstLetterLowercase}DTO));
            });
            return ${tableNameFirstLetterLowercase}EntityList;
        }
        return null;
    }

}
