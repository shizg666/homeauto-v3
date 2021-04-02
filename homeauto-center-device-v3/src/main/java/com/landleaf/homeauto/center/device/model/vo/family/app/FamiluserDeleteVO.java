package com.landleaf.homeauto.center.device.model.vo.family.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@ApiModel(value="FamiluserDeleteVO", description="FamiluserDeleteVO")
public class FamiluserDeleteVO {

    @ApiModelProperty("家庭id")
    private String familyId;

    @ApiModelProperty("成员id")
    private  String memberId;


}
