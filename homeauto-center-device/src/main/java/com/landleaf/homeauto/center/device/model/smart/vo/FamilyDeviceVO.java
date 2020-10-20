package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭设备信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDeviceVO {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("设备序列号")
    private String deviceSn;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备图标")
    private String deviceIcon;

    @ApiModelProperty("设备图片")
    private String deviceImage;

    @ApiModelProperty("设备索引")
    private Integer deviceIndex;

    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("品类编码")
    private String categoryCode;

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("房间ID")
    private String roomId;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("楼层ID")
    private String floorId;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("楼层号")
    private String floorNum;

    // --- 以下为兼容旧接口的字段 ---

    @Deprecated
    @ApiModelProperty("设备索引(兼容旧接口)")
    private Integer index;

    @Deprecated
    @ApiModelProperty("设备位置(兼容旧接口)")
    private String position;

    @Deprecated
    @ApiModelProperty("设备开关状态(兼容旧接口)")
    private Integer deviceSwitch;

    @Deprecated
    @ApiModelProperty("设备属性值(兼容旧接口)")
    private String deviceAttrString;


}
