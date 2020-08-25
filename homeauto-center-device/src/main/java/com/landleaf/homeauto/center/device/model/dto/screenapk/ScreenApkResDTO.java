package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "SmarthomeScreenApkResDTO",description = "大屏apk列表返回对象")
@Data
public class ScreenApkResDTO implements Serializable {

    private static final long serialVersionUID = 3794940680172259342L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "apk下载地址",required = true)
    private String url;

    @ApiModelProperty(value = "应用名称")
    private String name;

    @ApiModelProperty(value = "版本号(唯一标记)",required = true)
    private String versionCode;

    @ApiModelProperty(value = "上传者")
    private String uploadUser;

    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;

    @ApiModelProperty(value = "上传时间")
    private String uploadTimeFormat;



}
