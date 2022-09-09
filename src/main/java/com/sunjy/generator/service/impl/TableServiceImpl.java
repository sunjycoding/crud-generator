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
        //去掉 tb_, 作文件名前缀
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
            //这些字段不需要, 因为BaseEntity或者BaseDTO里面已经有了
            String[] notFillColumnArr = new String[]{"id", "create_user", "update_user", "create_time", "update_time"};
            Set<String> notFillColumnSet = Arrays.stream(notFillColumnArr).collect(Collectors.toSet());
            tableColumnDTOList = tableColumnDTOList.stream()
                    .filter(tableColumnDTO -> !notFillColumnSet.contains(tableColumnDTO.getColumnName()))
                    .toList();
            tableColumnDTOList.forEach(tableColumnDTO -> {
                //替换下划线
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
                //创建文件
                tempFile = createBackendFile(tableNameFirstLetterUppercase, templateData);
                zipFileName = "crud-backend.zip";
                break;
            case "frontend":
                templateData.put("moduleName", moduleName);
                templateData.put("routerPath", routerPath);
                templateData.put("tableNameFirstLetterLowercase", tableNameFirstLetterLowercase);
                templateData.put("tableNameFirstLetterUppercase", tableNameFirstLetterUppercase);
                //创建文件
                tempFile = createFrontendFile(tableNameFirstLetterUppercase, templateData);
                zipFileName = "crud-frontend.zip";
                break;
            default:
                throw new RuntimeException("请指定生成代码类型");
        }
        downloadZip(tempFile, response, zipFileName);
        CommonUtils.deleteDirectory(tempFile);
        //删除文件
        log.info("下载结束, 删除temp文件结果【{}】", !tempFile.exists());
    }

    private File createBackendFile(String filePrefix, Map<String, Object> templateData) {
        File backendTempFile = new File(Constants.BACKEND_TEMP_DIR_PATH);
        if (backendTempFile.exists()) {
            CommonUtils.deleteDirectory(backendTempFile);
        }
        //创建后端目录与文件
        BackendFileInfoEnum[] backendFileInfoEnums = BackendFileInfoEnum.values();
        for (BackendFileInfoEnum backendFileInfoEnum : backendFileInfoEnums) {
            String path = backendFileInfoEnum.getPath();
            String suffix = backendFileInfoEnum.getSuffix();
            String fileName = filePrefix + suffix;
            File fileDirectory = new File(path);
            if (!fileDirectory.exists()) {
                log.info("创建后端目录【{}】, 创建结果【{}】", path, fileDirectory.mkdirs());
            }
            File file = new File(path + fileName);
            try {
                log.info("创建后端文件【{}】, 创建结果【{}】", file.getName(), file.createNewFile());
                //写入后端模板
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fillTemplate(fileWriter, backendFileInfoEnum.getTemplateName(), templateData);
                } catch (Exception e) {
                    log.info("写入后端模板出现错误");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                log.info("创建后端目录或文件出现错误");
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
        //创建前端目录与文件
        FrontendFileInfoEnum[] frontendFileInfoEnums = FrontendFileInfoEnum.values();
        for (FrontendFileInfoEnum frontendFileInfoEnum : frontendFileInfoEnums) {
            String path = frontendFileInfoEnum.getPath();
            String suffix = frontendFileInfoEnum.getSuffix();
            String fileName = filePrefix + suffix;
            File fileDirectory = new File(path);
            if (!fileDirectory.exists()) {
                log.info("创建前端目录【{}】, 创建结果【{}】", path, fileDirectory.mkdirs());
            }
            File file = new File(path + fileName);
            try {
                log.info("创建前端文件【{}】, 创建结果【{}】", file.getName(), file.createNewFile());
                //写入前端模板
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fillTemplate(fileWriter, frontendFileInfoEnum.getTemplateName(), templateData);
                } catch (Exception e) {
                    log.info("写入前端模板出现错误");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                log.info("创建前端目录或文件出现错误");
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
        //转成压缩包输出流
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            String contentDisposition = String.format("attachment; filename=%s", zipFileName);
            response.setHeader("Content-Disposition", contentDisposition);
            CommonUtils.zip(file, zipOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
