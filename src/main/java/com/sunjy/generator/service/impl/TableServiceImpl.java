package com.sunjy.generator.service.impl;

import com.sunjy.generator.common.Constants;
import com.sunjy.generator.common.BackendFileInfoEnum;
import com.sunjy.generator.common.FrontendFileInfoEnum;
import com.sunjy.generator.common.MysqlTypeMapEnum;
import com.sunjy.generator.common.utils.CommonUtils;
import com.sunjy.generator.common.utils.ConnectionUtils;
import com.sunjy.generator.common.utils.DateUtils;
import com.sunjy.generator.dto.RequiredInfoDTO;
import com.sunjy.generator.dto.TableColumnDTO;
import com.sunjy.generator.dto.TableDTO;
import com.sunjy.generator.service.TableService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

/**
 * @author created by sunjy on 2022/9/7
 */
@Slf4j
@Service
public class TableServiceImpl implements TableService {

    private static final String BACKEND = "backend";

    private static final String FRONTEND = "frontend";

    @Override
    public List<TableDTO> list(String tableName) {
        List<TableDTO> tableDTOList = new ArrayList<>();
        Connection connection = ConnectionUtils.getConnection();
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String dbName = tables.getString("TABLE_CAT");
                if (ConnectionUtils.getDbName().equals(dbName)) {
                    String exactTableName = tables.getString("TABLE_NAME");
                    String remarks = tables.getString("REMARKS");
                    TableDTO tableDTO = TableDTO.builder()
                            .tableName(exactTableName)
                            .remarks(remarks)
                            .build();
                    if (StringUtils.isNotEmpty(tableName)) {
                        if (exactTableName.contains(tableName)) {
                            tableDTOList.add(tableDTO);
                        }
                    } else {
                        tableDTOList.add(tableDTO);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tableDTOList;
    }

    @Override
    public List<TableColumnDTO> listTableColumn(String tableName) {
        List<TableColumnDTO> tableColumnDTOList = new ArrayList<>();
        Connection connection = ConnectionUtils.getConnection();
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String typeName = columns.getString("TYPE_NAME");
                String columnSize = columns.getString("COLUMN_SIZE");
                String remarks = columns.getString("REMARKS");
                String javaType = MysqlTypeMapEnum.getJavaTypeByMysqlType(typeName);
                TableColumnDTO tableColumnDTO = TableColumnDTO.builder()
                        .columnName(columnName)
                        .typeName(typeName)
                        .javaTypeName(javaType)
                        .columnSize(columnSize)
                        .remarks(remarks)
                        .build();
                tableColumnDTOList.add(tableColumnDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tableColumnDTOList;
    }

    @Override
    public void generateBackendCode(String tableName, RequiredInfoDTO requiredInfoDTO, HttpServletResponse response) {
        generateCode(tableName, requiredInfoDTO, response, BACKEND);
    }

    @Override
    public void generateFrontendCode(String tableName, RequiredInfoDTO requiredInfoDTO, HttpServletResponse response) {
        generateCode(tableName, requiredInfoDTO, response, FRONTEND);
    }

    private void generateCode(String tableName, RequiredInfoDTO requiredInfoDTO, HttpServletResponse response, String type) {
        List<TableColumnDTO> tableColumnDTOList = this.listTableColumn(tableName);
        //?????? tb_, ??????????????????
        String dropTbTableName = tableName.replaceFirst("tb_", "");
        String tableNameFirstLetterLowercase = CommonUtils.underlineToCamelCaseFirstLowercase(dropTbTableName);
        String tableNameFirstLetterUppercase = CommonUtils.underlineToCamelCaseFirstUppercase(dropTbTableName);
        String author = requiredInfoDTO.getAuthor();
        String basePackage = requiredInfoDTO.getBasePackage();
        String moduleName = requiredInfoDTO.getModuleName();
        String routerPath = requiredInfoDTO.getRouterPath();
        String apiDescription = requiredInfoDTO.getApiDescription();
        String dtoDescription = requiredInfoDTO.getDtoDescription();

        Map<String, Object> templateData = new HashMap<>();

        if (CollectionUtils.isNotEmpty(tableColumnDTOList)) {
            //?????????????????????, ??????BaseEntity??????BaseDTO??????????????????
            String[] notFillColumnArr = new String[]{"id", "create_user", "update_user", "create_time", "update_time"};
            Set<String> notFillColumnSet = Arrays.stream(notFillColumnArr).collect(Collectors.toSet());
            tableColumnDTOList = tableColumnDTOList.stream()
                    .filter(tableColumnDTO -> !notFillColumnSet.contains(tableColumnDTO.getColumnName()))
                    .toList();
            tableColumnDTOList.forEach(tableColumnDTO -> {
                //???????????????
                tableColumnDTO.setColumnName(CommonUtils.underlineToCamelCaseFirstLowercase(tableColumnDTO.getColumnName()));
            });
            templateData.put("tableColumnDTOList", tableColumnDTOList);
        }
        File tempFile;
        String zipFileName;
        switch (type) {
            case "backend":
                templateData.put("tableName", tableName);
                templateData.put("moduleName", moduleName);
                templateData.put("author", author);
                templateData.put("basePackage", basePackage);
                templateData.put("date", DateUtils.now());
                templateData.put("routerPath", routerPath);
                templateData.put("apiDescription", apiDescription);
                templateData.put("dtoDescription", dtoDescription);
                templateData.put("tableNameFirstLetterLowercase", tableNameFirstLetterLowercase);
                templateData.put("tableNameFirstLetterUppercase", tableNameFirstLetterUppercase);
                //????????????
                tempFile = createBackendFile(tableNameFirstLetterUppercase, templateData);
                zipFileName = "crud-backend.zip";
                break;
            case "frontend":
                templateData.put("moduleName", moduleName);
                templateData.put("routerPath", routerPath);
                templateData.put("tableNameFirstLetterLowercase", tableNameFirstLetterLowercase);
                templateData.put("tableNameFirstLetterUppercase", tableNameFirstLetterUppercase);
                //????????????
                tempFile = createFrontendFile(tableNameFirstLetterUppercase, templateData);
                zipFileName = "crud-frontend.zip";
                break;
            default:
                throw new RuntimeException("???????????????????????????");
        }
        downloadZip(tempFile, response, zipFileName);
        CommonUtils.deleteDirectory(tempFile);
        //????????????
        log.info("????????????, ??????temp???????????????{}???", !tempFile.exists());
    }

    private File createBackendFile(String filePrefix, Map<String, Object> templateData) {
        File backendTempFile = new File(Constants.BACKEND_TEMP_DIR_PATH);
        if (backendTempFile.exists()) {
            CommonUtils.deleteDirectory(backendTempFile);
        }
        //???????????????????????????
        BackendFileInfoEnum[] backendFileInfoEnums = BackendFileInfoEnum.values();
        for (BackendFileInfoEnum backendFileInfoEnum : backendFileInfoEnums) {
            String path = backendFileInfoEnum.getPath();
            String suffix = backendFileInfoEnum.getSuffix();
            String fileName = filePrefix + suffix;
            File fileDirectory = new File(path);
            if (!fileDirectory.exists()) {
                log.info("?????????????????????{}???, ???????????????{}???", path, fileDirectory.mkdirs());
            }
            File file = new File(path + fileName);
            try {
                log.info("?????????????????????{}???, ???????????????{}???", file.getName(), file.createNewFile());
                //??????????????????
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fillTemplate(fileWriter, backendFileInfoEnum.getTemplateName(), templateData);
                } catch (Exception e) {
                    log.info("??????????????????????????????");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                log.info("???????????????????????????????????????");
                e.printStackTrace();
            }
        }
        return backendTempFile;
    }

    private File createFrontendFile(String filePrefix, Map<String, Object> templateData) {
        File frontendTempFile = new File(Constants.FRONTEND_TEMP_DIR_PATH);
        if (frontendTempFile.exists()) {
            CommonUtils.deleteDirectory(frontendTempFile);
        }
        //???????????????????????????
        FrontendFileInfoEnum[] frontendFileInfoEnums = FrontendFileInfoEnum.values();
        for (FrontendFileInfoEnum frontendFileInfoEnum : frontendFileInfoEnums) {
            String path = frontendFileInfoEnum.getPath();
            String suffix = frontendFileInfoEnum.getSuffix();
            String fileName = filePrefix + suffix;
            File fileDirectory = new File(path);
            if (!fileDirectory.exists()) {
                log.info("?????????????????????{}???, ???????????????{}???", path, fileDirectory.mkdirs());
            }
            File file = new File(path + fileName);
            try {
                log.info("?????????????????????{}???, ???????????????{}???", file.getName(), file.createNewFile());
                //??????????????????
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fillTemplate(fileWriter, frontendFileInfoEnum.getTemplateName(), templateData);
                } catch (Exception e) {
                    log.info("??????????????????????????????");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                log.info("???????????????????????????????????????");
                e.printStackTrace();
            }
        }
        return frontendTempFile;
    }

    private void fillTemplate(FileWriter fileWriter, String templateName, Map<String, Object> templateData) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
            configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
            configuration.setClassForTemplateLoading(this.getClass(), "/templates");
            Template template = configuration.getTemplate(templateName);
            template.process(templateData, fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadZip(File file, HttpServletResponse response, String zipFileName) {
        //????????????????????????
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            String contentDisposition = String.format("attachment; filename=%s", zipFileName);
            response.setHeader("Content-Disposition", contentDisposition);
            CommonUtils.zip(file, zipOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
