package com.hnbhbt.${moduleName}controller;

import com.hnbhbt.common.HttpResult;
import com.hnbhbt.common.PageParams;
import com.hnbhbt.${moduleName}.service.${tableNameFirstLetterUppercase}Service;
import com.hnbhbt.${moduleName}.dto.${tableNameFirstLetterUppercase}ConditionDTO;
import com.hnbhbt.${moduleName}.dto.${tableNameFirstLetterUppercase}DTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author created by ${author} on ${date}
 */
@Tag(name = "${apiDescription}")
@RequiredArgsConstructor
@RestController
@RequestMapping("${routerPath}")
public class ${tableNameFirstLetterUppercase}Controller {

    private final ${tableNameFirstLetterUppercase}Service ${tableNameFirstLetterLowercase}Service;

    @Operation(description = "分页列表")
    @GetMapping("page")
    public HttpResult page(PageParams pageParams, ${tableNameFirstLetterUppercase}ConditionDTO ${tableNameFirstLetterLowercase}ConditionDTO) {
        return HttpResult.success(${tableNameFirstLetterLowercase}Service.page(pageParams, ${tableNameFirstLetterLowercase}ConditionDTO));
    }

    @Operation(description = "列表")
    @GetMapping
    public HttpResult list() {
        return HttpResult.success(${tableNameFirstLetterLowercase}Service.list());
    }

    @Operation(description = "查询")
    @GetMapping("{id}")
    public HttpResult get(@PathVariable String id) {
        return HttpResult.success(${tableNameFirstLetterLowercase}Service.get(id));
    }

    @Operation(description = "新增")
    @PostMapping
    public HttpResult create(@Valid @RequestBody ${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}DTO) {
        ${tableNameFirstLetterLowercase}Service.create(${tableNameFirstLetterLowercase}DTO);
        return HttpResult.success();
    }

    @Operation(description = "修改")
    @PutMapping
    public HttpResult update(@Valid @RequestBody ${tableNameFirstLetterUppercase}DTO ${tableNameFirstLetterLowercase}DTO) {
        ${tableNameFirstLetterLowercase}Service.update(${tableNameFirstLetterLowercase}DTO);
        return HttpResult.success();
    }

    @Operation(description = "删除")
    @DeleteMapping("{id}")
    public HttpResult delete(@PathVariable String id) {
        ${tableNameFirstLetterLowercase}Service.delete(id);
        return HttpResult.success();
    }

    @Operation(description = "批量删除")
    @DeleteMapping
    public HttpResult delete(@RequestBody List<String> idList) {
        ${tableNameFirstLetterLowercase}Service.delete(idList);
        return HttpResult.success();
    }

}