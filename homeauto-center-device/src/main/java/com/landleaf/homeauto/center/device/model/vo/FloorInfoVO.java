package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.bo.FamilySimpleRoomBO;
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
@ApiModel(value="FloorInfoVO", description="FloorInfoVO")
public class FloorInfoVO {

    @ApiModelProperty("楼层")
    private String floor;

    @ApiModelProperty("房间列表")
    private List<RoomInfoVO> rooms;

}
