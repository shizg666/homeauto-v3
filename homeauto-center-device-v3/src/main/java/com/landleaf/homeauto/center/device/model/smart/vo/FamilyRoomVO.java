package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 户式化APP家庭房间视图对象
 *
 * @author Yujiumin
 * @version 2020/10/20
 */
@Data
@ApiModel("户式化APP家庭房间视图对象")
public class FamilyRoomVO {

    @ApiModelProperty("房间ID")
    private String roomId;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("房间图标")
    private String roomIcon;

    @ApiModelProperty(value = "小程序图片")
    private String imgApplets;

    @ApiModelProperty(value = "图片扩展预留")
    private String imgExpand;


}
