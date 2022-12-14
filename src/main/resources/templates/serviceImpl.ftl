package ${basePackage}.${moduleName}.service.impl;

import ${basePackage}.common.BaseConvertor;
import ${basePackage}.common.PageParams;
import ${basePackage}.common.Pagination;
import ${basePackage}.common.exception.SystemException;
import ${basePackage}.common.utils.PageUtils;
import ${basePackage}.service.${tableNameFirstLetterUppercase}Service;
import ${basePackage}.${moduleName}.dto.${tableNameFirstLetterUppercase}ConditionDTO;
import ${basePackage}.${moduleName}.dto.${tableNameFirstLetterUppercase}DTO;
import ${basePackage}.${moduleName}.entity.${tableNameFirstLetterUppercase}Entity;
import ${basePackage}.${moduleName}.repository.${tableNameFirstLetterUppercase}Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author created by ${author} on ${date}
 */
@RequiredArgsConstructor
@Service
public class ${tableNameFirstLetterUppercase}ServiceImpl implements ${tableNameFirstLetterUppercase}Service {

    private final ${tableNameFirstLetterUppercase}Repository ${tableNameFirstLetterLowercase}Repository;

    @Override
    public Pagination<${tableNameFirstLetterUppercase}DTO> page(PageParams pageParams, ${tableNameFirstLetterUppercase}ConditionDTO ${tableNameFirstLetterLowercase}ConditionDTO) {
        Page<${tableNameFirstLetterUppercase}Entity> ${tableNameFirstLetterLowercase}EntityPage = ${tableNameFirstLetterLowercase}Repository.findAll(PageUtils.buildRequest(pageParams));
        List<${tableNameFirstLetterUppercase}DTO> ${tableNameFirstLetterLowercase}DTOList = BaseConvertor.entityList2DtoList(${tableNameFirstLetterLowercase}EntityPage.getContent(), ${tableNameFirstLetterUppercase}DTO.class);
        return PageUtils.page(${tableNameFirstLetterLowercase}EntityPage, ${tableNameFirstLetterLowercase}DTOList);
    }

    @Override
    public List<${tableNameFirstLetterUppercase}DTO> list() {
        return BaseConvertor.entityList2DtoList(${tableNameFirstLetterLowercase}Repository.findAll(), ${tableNameFirstLetterUppercase}DTO.class);
    }

    @Override
    public ${tableNameFirstLetterUppercase}DTO get(String id) {
        return BaseConvertor.entity2Dto(${tableNameFirstLetterLowercase}Repository.findById(id).orElse(null), ${tableNameFirstLetterUppercase}DTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}DTO) {
        ${tableNameFirstLetterLowercase}Repository.save(BaseConvertor.dto2Entity(${tableNameFirstLetterLowercase}DTO, ${tableNameFirstLetterUppercase}Entity.class));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}DTO) {
        String id = ${tableNameFirstLetterLowercase}DTO.getId();
        if (${tableNameFirstLetterLowercase}Repository.existsById(id)) {
            ${tableNameFirstLetterLowercase}Repository.save(BaseConvertor.dto2Entity(${tableNameFirstLetterLowercase}DTO, ${tableNameFirstLetterUppercase}Entity.class));
        } else {
            throw new SystemException("???????????????, ????????????");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        ${tableNameFirstLetterLowercase}Repository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> idList) {
        ${tableNameFirstLetterLowercase}Repository.deleteAllById(idList);
    }

}
