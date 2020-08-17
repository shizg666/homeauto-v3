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


    @ApiModelProperty(value = "报修现象code")
    private String repairAppearance;

    @ApiModelProperty(value = "问题描述")
    private String description;

}
