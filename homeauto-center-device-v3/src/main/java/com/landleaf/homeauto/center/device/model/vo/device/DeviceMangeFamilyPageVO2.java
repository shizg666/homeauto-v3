package com.landleaf.homeauto.center.device.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 家庭设备
 * 新版本家庭设备表
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DeviceMangeFamilyPageVO2", description = "DeviceMangeFamilyPageVO2")
public class DeviceMangeFamilyPageVO2 {


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
    @ApiModelProperty(value = "设备所在楼层")
    private String deviceBelongFloor;
    @ApiModelProperty(value = "门牌")
    private String doorplate;
    @ApiModelProperty(value = "户型id")
    private Long templateId;
    @ApiModelProperty(value = "房间ID")
    private Long roomId;
    @ApiModelProperty(value = "房间名称")
    private String roomName;
    @ApiModelProperty(value = "设备id")
    private Long deviceId;
    @ApiModelProperty(value = "设备号")
    private String deviceSn;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备状态")
    private String online;

    @ApiModelProperty(value = "产品ID")
    private String productId;
    @ApiModelProperty(value = "产品Code号")
    private String productCode;

    @ApiModelProperty(value = "品类Code")
    private String categoryCode;
    @ApiModelProperty(value = "系统产品标志0普通设备； 1是系统下子设备；2是系统设备")
    private int systemFlag;




}
