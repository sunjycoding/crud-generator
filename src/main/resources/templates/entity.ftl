package com.hnbhbt.${moduleName}.entity;

import com.hnbhbt.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Created by ${author} on ${date}
 */
@Getter
@Setter
@Entity
@Table(name = "${tableName}")
public class ${tableNameFirstLetterUppercase}Entity extends BaseEntity {

    <#list tableColumnDTOList as tableColumn>
    /**
     * ${tableColumn.remarks}
     */
    private ${tableColumn.javaTypeName} ${tableColumn.columnName};

    </#list>
}
