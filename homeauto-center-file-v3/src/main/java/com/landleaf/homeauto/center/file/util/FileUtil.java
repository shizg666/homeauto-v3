package com.landleaf.homeauto.center.file.util;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.common.constant.DateFormatConst;
import com.landleaf.homeauto.common.util.ImageUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description:
 * @author: wyl
 **/
public class FileUtil {

    /**
     * 创建文件夹
     *
     * @param filderName
     */
    public static boolean createFilderIfNotExists(String filderName) throws Exception {
        File file = new File(filderName);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String extFile(String fileName) throws Exception {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 当前日期当文件夹名
     *
     * @return
     */
    public static String folderName() throws Exception {
        return DateFormatUtils.format(System.currentTimeMillis(), DateFormatConst.PATTERN_YYYYMMDD);
    }


    public static JSONObject fail() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 500);
        jsonObject.put("message", "文件上传失败");

        return jsonObject;
    }

    public static String writeFile(byte[] bytes, String filePath, String fileName) throws IOException {
        // 文件名字加上随机数
        int index = fileName.length() - 1;
        if (fileName.indexOf(".") != -1) {
            index = fileName.lastIndexOf(".");
        }
        fileName = fileName.substring(0, index) + "-"+RandomStringUtils.randomAlphabetic(6) + fileName.substring(index);

        String filePathName = filePath + fileName;
        Path target = Paths.get(filePathName);

        getPath(filePath);

        if (!Files.exists(target)) {
            Files.createFile(target);
        }

        Files.write(target, bytes);

        return filePathName;
    }

    public static String writeFileAndToSize(byte[] bytes, String filePath, String fileName, int length, int heigh, boolean keepAspectRatio) throws IOException {
        String tartgetFilePath = StringUtils.EMPTY;
        ;
        String tmpFilePath = StringUtils.EMPTY;
        try {
            tmpFilePath = writeFile(bytes, filePath, fileName);
            int index = tmpFilePath.lastIndexOf(".");
            tartgetFilePath = tmpFilePath.substring(0, index) + RandomStringUtils.randomAlphabetic(6) + tmpFilePath.substring(index);

            ImageUtil.toSize(new File(tmpFilePath), length, heigh, keepAspectRatio, new File(tartgetFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            File file = new File(tmpFilePath);
            if (file.exists()) {
                file.delete();
            }
        }
        return tartgetFilePath;
    }

    private static void getPath(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static String removeNonEnglish(String source) {
        if (StringUtils.isEmpty(source)) {
            return "default";
        }
        String reg = "[^a-zA-Z]";
        source = source.replaceAll(reg, "");
        return StringUtils.isEmpty(source) ? "default" : source;
    }
}
