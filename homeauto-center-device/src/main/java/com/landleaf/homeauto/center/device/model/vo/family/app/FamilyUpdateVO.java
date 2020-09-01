package com.landleaf.homeauto.center.device.model.vo.family.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@ApiModel(value="FamilyUpdateVO", description="FamilyUpdateVO")
public class FamilyUpdateVO {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("家庭名称")
    private  String name;


}
