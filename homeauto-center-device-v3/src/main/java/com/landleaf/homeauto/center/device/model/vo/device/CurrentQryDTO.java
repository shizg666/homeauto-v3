package com.landleaf.homeauto.center.device.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 家庭设备
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CurrentQryDTO", description = "查询当前状态")
public class CurrentQryDTO {

    @ApiModelProperty(value = "家庭ID",required = true)
    private Long familyId;

    @ApiModelProperty("属性编码")
    private String code;

    @ApiModelProperty("设备号")
    private String diviceSn;


}
