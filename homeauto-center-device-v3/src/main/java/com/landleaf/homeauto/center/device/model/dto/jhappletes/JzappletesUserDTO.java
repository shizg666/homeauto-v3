package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotEmpty;
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
@ApiModel(value="JzappletesUserDTO", description="用户对象")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JzappletesUserDTO implements Serializable {

    @ApiModelProperty(value = "用户主键id")
    private String userId;

    @NotEmpty(message = "用户名称不能为空")
    @ApiModelProperty(value = "用户名称")
    private String name;

    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty(value = "用户手机号")
    private String phone;
}
