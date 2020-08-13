package com.landleaf.homeauto.center.file.service.impl;

import com.landleaf.homeauto.center.file.service.IFileUploadService;
import com.landleaf.homeauto.center.file.util.FileUtil;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件上传服务接口实现
 * </p>
 */
@Service
public class FileUploadService implements IFileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);
    @Value("${homeauto.upload.base-path}")
    private String basePath;
    @Value("${homeauto.upload.pre-url}")
    private String picUrl;


    @Override
    public String singleUpload(MultipartFile file, String typeName) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCodeEnumConst.FILE_EMPTY_EXCEPTION);
        }
        String url = StringUtils.EMPTY;
        try {
            url = FileUtil.writeFile(file.getBytes(),
                    String.format("%s/%s/%s/", basePath, FileUtil.removeNonEnglish(typeName), FileUtil.folderName()),
                    file.getOriginalFilename());

        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnumConst.FILE_UPLOAD_FAIL);
        }
        return getFullUrl(url);
    }

    /**
     * 图片上传
     *
     * @param file
     * @param typeName
     * @return
     */
    @Override
    public String imageUpload(MultipartFile file, String typeName) {

        return singleUpload(file, typeName);
    }

    @Override
    public String imageUploadAndToSize(MultipartFile file, String typeName, int length, int heigh, boolean keepAspectRatio) {
        String url = StringUtils.EMPTY;
        try {
            url = FileUtil.writeFileAndToSize(file.getBytes(),
                    String.format("%s/%s/%s/", basePath, FileUtil.removeNonEnglish(typeName), FileUtil.folderName()),
                    file.getOriginalFilename(), length, heigh, keepAspectRatio);

        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnumConst.FILE_UPLOAD_FAIL);
        }
        return getFullUrl(url);
    }


    @Override
    public String getFilePath(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        String path = url.replace(picUrl, basePath);
        LOGGER.info("url {}，domainName {}，替换后的路径 {}", url, picUrl, path);
        return path;
    }

    @Override
    public String getFullUrl(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }
        //去掉basePath
        int index = filePath.indexOf(basePath);
        if (index != -1) {
            filePath = filePath.replace(basePath, "");
        }
        return picUrl + filePath;
    }
}
