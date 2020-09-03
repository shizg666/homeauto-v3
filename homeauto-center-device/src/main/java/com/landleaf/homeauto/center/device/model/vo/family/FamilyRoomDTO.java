package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型房间表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyRoomDTO", description="家庭房间")
public class FamilyRoomDTO {

    @ApiModelProperty(value = "房间id（修改必传）")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "楼层ID")
    private String floorId;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "房间ID")
    private String familyId;


}
