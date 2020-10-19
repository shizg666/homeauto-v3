package com.landleaf.homeauto.center.device.model.vo.family.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@ApiModel(value="FamiluseAddDTO", description="FamiluseAddDTO")
public class FamiluseAddDTO {

    @ApiModelProperty("家庭id/mac")
    private String family;

    @ApiModelProperty("类型 1 家庭id主键 2是家庭mac")
    private String type;


}
