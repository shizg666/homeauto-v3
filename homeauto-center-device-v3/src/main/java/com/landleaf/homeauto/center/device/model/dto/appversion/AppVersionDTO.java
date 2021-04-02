package com.landleaf.homeauto.center.device.model.dto.appversion;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Lokiy
 * @date 2019/10/31 14:28
 * @description:
 */
@Data
@NoArgsConstructor
@ToString
@ApiModel("app版本返回")
public class AppVersionDTO implements Serializable {


    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty("app版本")
    private String version;

    @ApiModelProperty("app版本描述")
    private String description;

    @ApiModelProperty(value = "APP类型 1-android 2-ios",notes = "APP类型 1-android 2-ios")
    private Integer appType;

    @ApiModelProperty("强制标识")
    private Integer forceFlag;

    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime versionTime;

    @ApiModelProperty("文件路径")
    private String url;

    @ApiModelProperty(value = "是否已推送（1：已推送，0：未推送）",notes = "是否已推送（1：已推送，0：未推送）")
    private Integer pushStatus;

    @ApiModelProperty(value = "所属app(smart,non-smart)",notes = "所属app(智能家居：smart,自由方舟:non-smart)")
    private String belongApp;

    @ApiModelProperty(value = "推送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime pushTime;

    @ApiModelProperty(value = "当前版本标记")
    private Integer currentFlag;

    @ApiModelProperty(value = "上传人")
    private String uploadUser;

    @ApiModelProperty("APP类型描述")
    private String appTypeDesc;

    @ApiModelProperty("强制标识描述")
    private String forceFlagDesc;

    @ApiModelProperty("是否已推送")
    private String pushStatusDesc;

    @ApiModelProperty("是当前版本")
    private String currentFlagDesc;

}
