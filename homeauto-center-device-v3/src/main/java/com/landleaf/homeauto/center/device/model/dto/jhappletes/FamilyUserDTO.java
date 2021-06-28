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
@ApiModel(value="FamilyUserDTO", description="绑定家庭")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamilyUserDTO implements Serializable {


    @NotNull(message = "楼栋号不能为空")
    @ApiModelProperty(value = "楼栋")
    private String buildCode;

    @NotNull(message = "单元不能为空")
    @ApiModelProperty(value = "单元")
    private String unitCode;

    @NotNull(message = "门牌号不能为空")
    @ApiModelProperty(value = "门牌号")
    private String doorplate;

    @ApiModelProperty(value = "用户名称")
    private String name;

    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty(value = "用户手机号")
    private String userPhone;

    @ApiModelProperty(value = "1 户主 3普通成员 添加成员类型必填")
    private Integer userType;

    @NotEmpty(message = "操作类型")
    @ApiModelProperty(value = "1添加成员 2删除成员")
    private Integer operateType;

}
