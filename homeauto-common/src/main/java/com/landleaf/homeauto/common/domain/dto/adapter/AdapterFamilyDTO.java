package com.landleaf.homeauto.common.domain.dto.adapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 家庭信息
 *
 * @author wenyilu
 */
@Data
public class AdapterFamilyDTO {

    @ApiModelProperty(value = "家庭编号")
    private String familyCode;

    @ApiModelProperty(value = "家庭主键")
    private String familyId;


}
