package com.hnbhbt.${moduleName}.dto;

import com.hnbhbt.common.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author created by ${author} on ${date}
 */
@Schema(description = "${dtoDescription}")
@EqualsAndHashCode(callSuper = true)
@Data
public class ${tableNameFirstLetterUppercase}DTO extends BaseDTO {

    <#list tableColumnDTOList as tableColumn>
    @Schema(description = "${tableColumn.remarks}")
    private ${tableColumn.javaTypeName} ${tableColumn.columnName};

    </#list>
}
