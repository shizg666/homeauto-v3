package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="HouseFloorRoomListVO", description="HouseFloorRoomListVO")
public class HouseFloorRoomListVO {

    @ApiModelProperty("楼层")
    private List<String> floors;

    @ApiModelProperty("房间")
    private List<String> rooms;







}
