package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@NoArgsConstructor
@ApiModel("楼层房间试图对象")
public class FloorRoomVO {

    @ApiModelProperty("楼层")
    private String floor;

    @ApiModelProperty("房间列表")
    private List<RoomDeviceVO> rooms;

}
