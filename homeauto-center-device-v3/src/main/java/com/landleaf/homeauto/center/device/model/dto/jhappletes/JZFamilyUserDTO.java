package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName JzappletesUserDTO
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/22
 * @Version V1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel(value="JZFamilyUserDTO", description="添加成员")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JZFamilyUserDTO implements Serializable {

    @NotEmpty(message = "用户id不能为空")
    @ApiModelProperty(value = "用户主键id")
    private String userId;

    @NotNull(message = "家庭id不能为空")
    @ApiModelProperty(value = "家庭id")
    private Long familyId;

}
