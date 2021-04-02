package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.bo.SimpleFamilyBO;
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
@ApiModel("家庭视图对象")
public class FamilyVO {

    @ApiModelProperty("当前显示的家庭")
    private SimpleFamilyBO current;

    @ApiModelProperty("家庭列表")
    private List<SimpleFamilyBO> list;

}
