package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoAppVersionDO对象", description="")
@TableName("home_auto_app_version")
public class HomeAutoAppVersionDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "app版本")
    private String version;

    @ApiModelProperty(value = "app版本描述")
    private String description;

    @ApiModelProperty(value = "APP类型 1-andriod 2-ios")
    private Integer appType;

    @ApiModelProperty(value = "强制标识")
    private Integer forceFlag;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime versionTime;

    @ApiModelProperty(value = "文件路径")
    private String url;

    @ApiModelProperty(value = "是否已推送（1：已推送，0：未推送）")
    private Integer pushStatus;

    @ApiModelProperty(value = "所属app（smart,non-smart）")
    private String belongApp;
    @ApiModelProperty(value = "推送时间")
    private LocalDateTime pushTime;
    @ApiModelProperty(value = "当前版本标记")
    private Integer currentFlag;


}
