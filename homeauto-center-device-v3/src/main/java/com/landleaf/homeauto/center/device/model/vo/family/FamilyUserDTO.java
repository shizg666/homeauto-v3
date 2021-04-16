package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
@ApiModel(value="FamilyUserDTO", description="")
public class FamilyUserDTO {

    @ApiModelProperty(value = "家庭id",required = true)
    private Long familyId;

    @NotEmpty(message ="客户id不能为空")
    @ApiModelProperty(value = "客户id",required = true)
    private String userId;

    @ApiModelProperty(value = "类型1 管理员账户  2 运维人员 3普通用户")
    private Integer type = 3;


}
