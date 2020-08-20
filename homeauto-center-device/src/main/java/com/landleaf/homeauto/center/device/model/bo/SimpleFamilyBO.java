package com.landleaf.homeauto.center.device.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单家庭业务对象
 */
@Data
@NoArgsConstructor
public class SimpleFamilyBO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("家庭名称")
    private String familyName;

}
