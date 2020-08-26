package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamiluserDeleteVO", description="FamiluserDeleteVO")
public class FamiluserDeleteVO {

    @ApiModelProperty("家庭id")
    private String familyId;

    @ApiModelProperty("成员id")
    private  String menberId;


}
