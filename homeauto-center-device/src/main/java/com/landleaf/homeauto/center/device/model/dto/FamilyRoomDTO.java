package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 家庭房间对象
 *
 * @author Yujiumin
 * @version 2020/9/14
 */
@Data
@ApiModel("房间数据传输对象")
public class FamilyRoomDTO {

    @ApiModelProperty("房间ID")
    private String roomId;

    @ApiModelProperty("房间图片")
    private String roomPic;

}
