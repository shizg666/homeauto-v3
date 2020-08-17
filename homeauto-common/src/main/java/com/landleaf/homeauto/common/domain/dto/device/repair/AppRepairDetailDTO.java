package com.landleaf.homeauto.common.domain.dto.device.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: app故障报修详情DTO
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@ApiModel("app故障报修详情DTO")
@Data
public class AppRepairDetailDTO {

    @ApiModelProperty(value = "记录id")
    private String repairId;

    @ApiModelProperty(value = "报修现象code")
    private String repairAppearance;

    @ApiModelProperty(value = "问题描述")
    private String description;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "状态中文名称")
    private String statusName;

}
