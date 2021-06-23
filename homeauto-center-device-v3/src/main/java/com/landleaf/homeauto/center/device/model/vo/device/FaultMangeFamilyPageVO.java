package com.landleaf.homeauto.center.device.model.vo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 家庭设备故障
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "FaultMangeFamilyPageVO", description = "FaultMangeFamilyPageVO")
public class FaultMangeFamilyPageVO {


    @ApiModelProperty("家庭号")
    private Long familyId;

    @ApiModelProperty("楼盘家庭编号类似3204040101-0101501")
    private String familyCode;
    @ApiModelProperty("项目ID")
    private Long projectId;
    @ApiModelProperty("楼栋号")
    private String buildingCode;
    @ApiModelProperty(value = "单元编号")
    private String unitCode;
    @ApiModelProperty(value = "门牌")
    private String doorplate;
    @ApiModelProperty(value = "业主姓名")
    private String name;
    @ApiModelProperty(value = "房间ID")
    private Long roomId;
    @ApiModelProperty(value = "房间名称")
    private String roomName;
    @ApiModelProperty(value = "设备号")
    private String deviceSn;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "故障信息")
    private String faultMsg;
    @ApiModelProperty(value = "故障时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime faultTime;

    @ApiModelProperty(value = "户型id")
    private Long templateId;

    @ApiModelProperty(value = "品类Code")
    private String categoryCode;




}
