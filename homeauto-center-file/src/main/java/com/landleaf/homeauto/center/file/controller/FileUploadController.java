package com.landleaf.homeauto.center.file.controller;

import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.file.service.IFileUploadService;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.RichTextImageResponse;
import com.landleaf.homeauto.common.domain.vo.file.FileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通用文件上传控制器
 * </p>
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("file/upload")
public class FileUploadController extends BaseController {


    @Autowired
    private IFileUploadService fileUploadService;

    /**
     * 文件上传
     *
     * @return
     */
    @PostMapping("/normal")
    @ApiOperation(value = "文件上传", notes = "文件上传", produces = "multipart/form-data")
    public Response singleUpload(FileVO fileVO) throws Exception {
        Map<String, String> result = Maps.newHashMap();
        String url = fileUploadService.singleUpload(fileVO.getFile(), fileVO.getTypeName());
        result.put("url", url);
        return returnSuccess(result);
    }

    /**
     * apk上传
     *
     * @return
     */
    @PostMapping("apk")
    @ApiOperation(value = "apk上传", notes = "apk上传", produces = "multipart/form-data")
    public Response apkUpload(FileVO fileVO) throws Exception {
        Map<String, String> result = Maps.newHashMap();
        String url = fileUploadService.singleUpload(fileVO.getFile(), "apk");
        result.put("url", url);
        return returnSuccess(result);
    }

    /**
     * 图片上传
     *
     * @return
     */
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "图片上传", notes = "图片上传", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response imageUpload(FileVO fileVO) throws Exception {
        Map<String, String> result = Maps.newHashMap();
        String url = fileUploadService.imageUpload(fileVO.getFile(), fileVO.getTypeName());
        result.put("url", url);
        return returnSuccess(result);
    }


    /**
     * 富文本图片上传
     *
     * @param fileVO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/rich-text-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "富文本图片上传", notes = "富文本图片上传", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RichTextImageResponse richTextImageUpload(FileVO fileVO) {

        String url = fileUploadService.imageUpload(fileVO.getFile(), fileVO.getTypeName());
//        String url = fileUploadService.imageUploadAndToSize(fileVO.getFile(), fileVO.getTypeName(),250,250,true);
        List<String> urls = new ArrayList<String>() {{
            add(url);
        }};
        return new RichTextImageResponse(RichTextImageResponse.SUCCESS_ERRNO, urls);
    }


}
