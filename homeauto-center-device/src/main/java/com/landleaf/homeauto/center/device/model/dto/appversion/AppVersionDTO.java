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

    @ApiModelProperty("APP类型 1-android 2-ios")
    private Integer appType;

    @ApiModelProperty("强制标识")
    private Integer forceFlag;

    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime versionTime;

    @ApiModelProperty("文件路径")
    private String url;

    @ApiModelProperty("是否启用标识")
    private Integer enableFlag;

    @ApiModelProperty(value = "是否已推送（1：已推送，0：未推送）")
    private Integer pushStatus;


    @ApiModelProperty("APP类型描述")
    private String appTypeDesc;

    @ApiModelProperty("强制标识描述")
    private String forceFlagDesc;

    @ApiModelProperty("启用标识描述")
    private String enableFlagDesc;
    @ApiModelProperty("是否已推送")
    private String pushStatusDesc;
}
