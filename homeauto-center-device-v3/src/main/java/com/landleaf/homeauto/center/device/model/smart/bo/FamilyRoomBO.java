package com.landleaf.homeauto.center.device.model.smart.bo;

import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/10/19
 */
@Data
@ApiModel("户式化APP家庭房间业务对象")
public class FamilyRoomBO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("家庭编码")
    private String familyCode;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("户型编号")
    private String templateId;

    @ApiModelProperty("楼层ID")
    private String floorId;

    @ApiModelProperty("楼层号")
    private String floorNum;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("房间ID")
    private String roomId;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("区域编号")
    private String roomCode;

    @ApiModelProperty("房间壹号图标")
    private String roomIcon1;

    @ApiModelProperty("房间贰号图标")
    private String roomIcon2;

    @ApiModelProperty(value = "小程序图片")
    private String imgApplets;

    @ApiModelProperty(value = "图片扩展预留")
    private String imgExpand;

    @ApiModelProperty("房间类型")
    private RoomTypeEnum roomTypeEnum;

    @ApiModelProperty("房间设备列表")
    private List<FamilyDeviceBO> familyDeviceBOList;

}
