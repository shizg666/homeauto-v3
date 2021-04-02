package com.landleaf.homeauto.common.domain.dto.device.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 新增报修数据传输DTO
 *
 * @author wenyilu*/
@Data
@ApiModel(value="RepairAddReqDTO", description="新增报修数据传输DTO")
public class RepairAddReqDTO {


    @ApiModelProperty(value = "报修设备")
    private String deviceName;

    @ApiModelProperty(value = "故障内容下拉选项code值")
    private String contentCode;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

}
