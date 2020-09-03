package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 工程用户关联表
 * </p>
 *
 * @author lokiy
 * @since 2019-08-23
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyUserOperateDTO", description="FamilyUserOperateDTO")
public class FamilyUserOperateDTO {

    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @ApiModelProperty(value = "主键id")
    private String id;



}
