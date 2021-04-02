package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 家庭表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyOperateDTO", description="家庭操作对象")
public class FamilyOperateDTO {

    @ApiModelProperty(value = "家庭id")
    private String id;

    @NotEmpty(message = "项目id必传")
    @ApiModelProperty(value = "项目id（必传）")
    private String projectId;


}
