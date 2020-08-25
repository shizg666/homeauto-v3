package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "SmarthomeScreenApkSaveDTO",description = "大屏apk新增对象")
@Data
public class ScreenApkSaveDTO implements Serializable {
    private static final long serialVersionUID = 8883758835540686233L;

    @ApiModelProperty(value = "apk名称",required = true)
    private String name;

    @ApiModelProperty(value = "apk下载地址",required = true)
    private String url;

    @ApiModelProperty(value = "版本号(唯一标记)",required = true)
    private String versionCode;




}
