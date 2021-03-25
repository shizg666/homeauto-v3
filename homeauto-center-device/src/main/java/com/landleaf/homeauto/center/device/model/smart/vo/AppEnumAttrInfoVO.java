package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * app枚举性属性展示对象
 * </p>
 *
 * @author pilo
 */
@Data
@ApiModel(value="AppEnumAttrInfoVO", description="app枚举性属性展示对象")
public class AppEnumAttrInfoVO {

    @ApiModelProperty(value = "品类")
    private Object currentValue;
    @ApiModelProperty(value = "未选值")
    private List<String> selects;


}
