package com.landleaf.homeauto.center.device.model.vo;

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
@ApiModel(value="MyFamilyDetailInfoVO", description="我的家庭详细信息对象")
public class MyFamilyDetailInfoVO {

    @ApiModelProperty("家庭主键id")
    private String id;

    @ApiModelProperty("家庭名称")
    private String name;


}
