package com.sunjy.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author created by sunjy on 2022/9/8
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MysqlInfoDTO {

    @NonNull
    private String ip;

    @NonNull
    private String port;

    @NonNull
    private String dbName;

    @NonNull
    private String user;

    @NonNull
    private String password;

}
