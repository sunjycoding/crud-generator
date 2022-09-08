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
public class TableDTO {

    private String tableName;

    private String remarks;

}
