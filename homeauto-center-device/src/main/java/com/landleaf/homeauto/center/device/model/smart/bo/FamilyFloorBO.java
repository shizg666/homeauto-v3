package com.landleaf.homeauto.center.device.model.smart.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 家庭楼层业务对象
 *
 * @author Yujiumin
 * @version 2020/10/20
 */
@Data
@ApiModel("户式化APP家庭房间业务对象")
public class FamilyFloorBO {

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

    @ApiModelProperty("楼层房间列表")
    private List<FamilyRoomBO> familyRoomBOList;

}
