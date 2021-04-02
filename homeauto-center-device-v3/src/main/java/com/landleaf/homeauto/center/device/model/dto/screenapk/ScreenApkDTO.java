package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "SmarthomeScreenApkUpdateDTO",description = "大屏apk更新对象")
@Data
public class ScreenApkDTO implements Serializable {

    private static final long serialVersionUID = 5864714089968701976L;
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "应用名称",required = true)
    private String name;

    @ApiModelProperty(value = "apk下载地址",required = true)
    private String url;

    @ApiModelProperty(value = "版本号(唯一标记)",required = true)
    private String versionCode;




}
