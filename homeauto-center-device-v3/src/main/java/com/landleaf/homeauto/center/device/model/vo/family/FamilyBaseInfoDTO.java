package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyBaseInfoDTO", description="家庭基本信息")
public class FamilyBaseInfoDTO {

    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @ApiModelProperty(value = "家庭code")
    private String code;



}
