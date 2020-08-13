package com.landleaf.homeauto.center.oauth.remote;

import com.landleaf.homeauto.common.feign.FeignMultipartSupportConfig;
import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.RichTextImageResponse;
import com.landleaf.homeauto.common.domain.vo.file.FileVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_FILE, configuration = FeignMultipartSupportConfig.class)
public interface FileRemote {

    @PostMapping(value = "/file/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "app头像上传", notes = "app头像上传", consumes = "multipart/form-data")
    public Response imageUpload(@RequestPart(value = "file") FileVO file);


    @PostMapping(value = "/file/upload/rich-text-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "富文本图片上传", notes = "富文本图片上传", consumes = "multipart/form-data")
    RichTextImageResponse richTextImageUpload(@RequestPart(value = "file") FileVO file);

    /**
     * apk上传
     *
     * @return
     */
    @PostMapping(value = "/file/upload/apk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "apk上传", notes = "apk上传", produces = "multipart/form-data")
    public Response apkUpload(@RequestPart("file") FileVO file);
}
