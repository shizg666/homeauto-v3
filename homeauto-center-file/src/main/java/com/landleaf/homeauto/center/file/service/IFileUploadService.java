package com.landleaf.homeauto.center.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件上传服务接口
 * </p>
 */
public interface IFileUploadService {


    String singleUpload(MultipartFile file, String typeName);

    String imageUpload(MultipartFile file, String typeName);

    /**
     * 保存指定大小 图片
     *
     * @param file            源文件
     * @param typeName        文件存储头层目录
     * @param length          宽度
     * @param heigh           高度
     * @param keepAspectRatio 是否原比例
     * @return
     */
    String imageUploadAndToSize(MultipartFile file, String typeName, int length, int heigh, boolean keepAspectRatio);

    /**
     * 获取可直接访问的url
     */
    String getFullUrl(String filePath);

    /**
     * 获取文件存储路径
     */
    String getFilePath(String url);

}
