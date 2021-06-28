package com.landleaf.homeauto.center.device.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 家庭设备
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CurrentQryDTO2", description = "查询当前状态")
public class CurrentQryDTO2 {

    @ApiModelProperty(value = "家庭ID",required = true)
    private Long familyId;

    @ApiModelProperty("属性编码列表")
    private List<String> codes;

    @ApiModelProperty("设备号")
    private String deviceSn;


}
