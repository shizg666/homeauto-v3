package com.landleaf.homeauto.center.device.model.vo.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/10/14
 */
@Data
@ApiModel("房间视图对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomVO {

    @ApiModelProperty("房间ID")
    private String roomId;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("房间图标")
    private String roomIcon;

}
