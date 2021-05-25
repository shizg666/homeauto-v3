package com.landleaf.homeauto.center.device.model.vo.maintenance;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@ApiModel(value = "FamilyMaintenanceRecordVO", description = "维保记录返回VO")
public class FamilyMaintenanceRecordVO {



    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "家庭")
    private Long familyId;

    @ApiModelProperty(value = "年月日加6位流水号")
    private Long num;

    @ApiModelProperty(value = "维保时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDate maintenanceTime;

    @ApiModelProperty(value = "维保类别")
    private Integer maintenanceType;
    @ApiModelProperty(value = "维保类别(1:手动录入，2:移动端上报)")
    private String maintenanceTypeDsc;

    @ApiModelProperty(value = "维保内容")
    private String content;

    @ApiModelProperty(value = "业主姓名")
    private String owner;

    @ApiModelProperty(value = "联系方式")
    private String mobile;

    @ApiModelProperty("门牌号")
    private String familyNumber;

    @ApiModelProperty("单元号")
    private String unitCode;

    @ApiModelProperty("楼栋号")
    private String buildingCode;
    @ApiModelProperty("房屋path")
    private String locatePath;

}
