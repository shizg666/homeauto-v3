package com.landleaf.homeauto.center.device.model.vo.device;

import com.landleaf.homeauto.center.device.enums.OnlineStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
@ApiModel(value = "FamilyDeviceDetailVO", description = "FamilyDeviceDetailVO")
public class FamilyDeviceDetailVO {

//    @ApiModelProperty("楼盘ID")
//    private Long realestateId;
//    @ApiModelProperty("项目ID")
//    private Long projectId;
//    @ApiModelProperty("楼栋号")
//    private String buildingCode;
//    @ApiModelProperty(value = "房屋编号")
//    private String familyCode;
//    @ApiModelProperty(value = "房屋名称")
//    private String familyName;
//    @ApiModelProperty(value = "房屋ID")
//    private Long familyId;
//    @ApiModelProperty(value = "户型id")
//    private Long templateId;
//    @ApiModelProperty(value = "房间ID")
//    private Long roomId;
    @ApiModelProperty(value = "房间名称")
    private String roomName;
//    @ApiModelProperty(value = "设备所在楼层")
//    private String deviceBelongFloor;
    @ApiModelProperty(value = "设备ID")
    private String deviceId;
    @ApiModelProperty(value = "设备号")
    private String deviceSn;
    @ApiModelProperty(value = "设备code")
    private String addressCode;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    @ApiModelProperty(value = "系统设备名称")
    private String sysProductName;

    @ApiModelProperty(value = "产品名称")
    private String ProductName;

    @ApiModelProperty(value = "品牌")
    private String brandName;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

    @ApiModelProperty(value = "在线 1在线 0离线")
    private Integer onlineFlag;
    @ApiModelProperty(value = "在线 1在线 0离线")
    private String onlineFlagStr
            ;

    public Integer getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(Integer onlineFlag) {
        this.onlineFlag = onlineFlag;
        if(Objects.isNull(onlineFlag)){
            this.onlineFlagStr = "-";
        }else {
            this.onlineFlagStr = OnlineStatusEnum.getInstByType(onlineFlag) ==null?"-":OnlineStatusEnum.getInstByType(onlineFlag).getName();
        }

    }
}
