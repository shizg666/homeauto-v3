package com.landleaf.homeauto.center.device.model.dto.screenapk;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "ProjectScreenUpgradeInfoDetailDTO",description = "upgrade详情")
@Data
public class ProjectScreenUpgradeInfoDetailDTO implements Serializable {
    private static final long serialVersionUID = 8883758835540686233L;

    @ApiModelProperty(value = "推送记录ID")
    private Long detailId;
    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "门牌号")
    private String doorplate;

    @ApiModelProperty(value = "单元code")
    private String unitCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    @ApiModelProperty(value = "推送时间（以时间判定为当前推送版本）")
    private LocalDateTime pushTime;
    @ApiModelProperty(value = "目前版本")
    private String versionCode;
    @ApiModelProperty(value = "更新状态（1：未完成；2：已完成）")
    private Integer status;
    @ApiModelProperty(value = "更新状态（1：未完成；2：已完成）")
    private String statusName;




}
