package com.sunjy.generator.common.utils;

import com.sunjy.generator.common.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author created by sunjy on 2022/9/7
 */
public class CommonUtils {

    /**
     * 下划线格式字符串转驼峰, 首字母大写
     *
     * @param originStr
     * @return
     */
    public static String underlineToCamelCaseFirstUppercase(String originStr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotEmpty(originStr)) {
            //先全部转小写
            originStr = originStr.toLowerCase();
            String[] strArr = originStr.split(Constants.UNDERLINE);
            for (String str : strArr) {
                //每一个字符串的第一个字母改成大写
                String firstLetter = String.valueOf(str.charAt(0));
                String firstLetterUppercase = firstLetter.toUpperCase();
                String firstLetterUppercaseStr = firstLetterUppercase + str.substring(1);
                stringBuilder.append(firstLetterUppercaseStr);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 下划线格式字符串转驼峰, 首字母小写
     *
     * @param originStr
     * @return
     */
    public static String underlineToCamelCaseFirstLowercase(String originStr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotEmpty(originStr)) {
            //先全部转小写
            originStr = originStr.toLowerCase();
            String[] strArr = originStr.split(Constants.UNDERLINE);
            stringBuilder.append(strArr[0]);
            for (int i = 1; i < strArr.length; i++) {
                String str = strArr[i];
                String firstLetter = String.valueOf(str.charAt(0));
                String firstLetterUppercase = firstLetter.toUpperCase();
                String firstLetterUppercaseStr = firstLetterUppercase + str.substring(1);
                stringBuilder.append(firstLetterUppercaseStr);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 删除文件夹以及下面所有文件
     *
     * @param file
     */
    public static void deleteDirectory(File file) {
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteDirectory(f);
                    }
                    f.delete();
                }
            }
        }
        file.delete();
    }

    /**
     * 压缩文件
     *
     * @param sourceFile 源文件或文件夹
     */
    public static void zip(File sourceFile, ZipOutputStream zipOutputStream) {
        if (sourceFile.isFile()) {
            byte[] buff = new byte[2048];
            try (FileInputStream fileInputStream = new FileInputStream(sourceFile)) {
                zipOutputStream.putNextEntry(new ZipEntry(sourceFile.getParent() + File.separator + sourceFile.getName()));
                int len;
                while ((len = fileInputStream.read(buff)) != -1) {
                    zipOutputStream.write(buff, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            File[] files = sourceFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    zip(file, zipOutputStream);
                }
            }
        }
    }

}
