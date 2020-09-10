package com.landleaf.homeauto.common.domain.dto.device.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/9/10
 */
@Data
@ApiModel("家庭授权状态")
public class FamilyAuthStatusDTO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("审核状态")
    private Integer status;

}
