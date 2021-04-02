package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/10/20
 */
@Data
@ApiModel("户式化APP家庭楼层视图对象")
public class FamilyFloorVO {

    @ApiModelProperty("家庭ID")
    private String familyId;
    @ApiModelProperty("户型ID")
    private String templateId;

    @ApiModelProperty("楼层ID")
    private String floorId;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("楼层房间列表")
    private List<FamilyRoomVO> roomList;

}
