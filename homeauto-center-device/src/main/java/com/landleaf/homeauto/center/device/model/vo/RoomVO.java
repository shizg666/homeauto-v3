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
@ApiModel("家庭房间视图层对象")
public class RoomVO {

    @ApiModelProperty("楼层ID")
    private String floorId;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("楼层号")
    private String floorNum;

    @ApiModelProperty("房间信息")
    private List<FamilySimpleRoomBO> roomList;

}
