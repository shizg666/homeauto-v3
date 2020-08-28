package com.landleaf.homeauto.common.domain.dto.device.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

    @ApiModelProperty(value = "报修单号")
    private String ticketId;

    @ApiModelProperty(value = "报修设备")
    private String deviceName;

    @ApiModelProperty(value = "报修日期")
    private String repairTime;

    @ApiModelProperty(value = "故障内容")
    private String content;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "操作日志")
    private List<AppRepairDetailLogDTO> logs;

}
