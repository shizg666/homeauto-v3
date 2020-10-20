package com.landleaf.homeauto.center.device.model.vo.family.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@ApiModel(value="FamilyUpdateVO", description="FamilyUpdateVO")
public class FamilyUpdateVO {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("名称")
    @NotEmpty(message = "名称不能为空")
    @Length(min=1, max=5,message = "名称不能超过五个字符")
    private  String name;


}
