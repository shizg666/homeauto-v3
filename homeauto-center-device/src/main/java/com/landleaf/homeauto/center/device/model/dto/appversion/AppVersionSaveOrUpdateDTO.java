package com.landleaf.homeauto.center.device.model.dto.appversion;

import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/10/31 14:28
 * @description:
 */
@Data
@NoArgsConstructor
@ToString
@ApiModel("AppVersionSaveOrUpdateDTO")
public class AppVersionSaveOrUpdateDTO implements Serializable {


    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty("app版本")
    private String version;

    @ApiModelProperty(value = "APP类型 1-android 2-ios", notes = "APP类型 1-android 2-ios")
    private Integer appType;

    @ApiModelProperty(value = "强制标识1:是，0：否",notes = "1:是，0：否")
    private Integer forceFlag;

    @ApiModelProperty("文件路径")
    private String url;

    @ApiModelProperty(value = "所属app（smart,non-smart）",notes = "所属app（smart,non-smart）")
    private String belongApp= AppTypeEnum.SMART.getCode();

    @ApiModelProperty("app版本描述")
    private String description;

}
