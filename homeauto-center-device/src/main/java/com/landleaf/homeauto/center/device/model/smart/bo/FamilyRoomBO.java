package com.landleaf.homeauto.center.device.model.smart.bo;

import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty("房间壹号图标")
    private String roomIcon1;

    @ApiModelProperty("房间贰号图标")
    private String roomIcon2;

    @ApiModelProperty("房间类型")
    private RoomTypeEnum roomTypeEnum;

}
