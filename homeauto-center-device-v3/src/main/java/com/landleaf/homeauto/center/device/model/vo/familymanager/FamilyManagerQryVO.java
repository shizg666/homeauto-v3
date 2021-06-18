package com.landleaf.homeauto.center.device.model.vo.familymanager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamilyManagerQryVO", description="FamilyManagerQryVO")
public class FamilyManagerQryVO {

    @ApiModelProperty(value = "家庭主键id")
    private Long familyId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;




}
