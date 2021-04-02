package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName FamilyModeScopeVO
 * @Description: 家庭模式下温度值域
 * @Author wyl
 * @Date 2021/1/6
 * @Version V2.0
 **/
@Data
@ApiModel("家庭模式下温度值域")
public class FamilyModeScopeVO {
    @ApiModelProperty("模式(制冷:cold,制热:hot)")
    private String code;
    @ApiModelProperty("最小值")
    private String min;
    @ApiModelProperty("最大值")
    private String max;

}
