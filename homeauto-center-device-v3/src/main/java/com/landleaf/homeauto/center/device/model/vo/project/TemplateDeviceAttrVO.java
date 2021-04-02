package com.landleaf.homeauto.center.device.model.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateDeviceAttrVO", description="户型设备属性")
public class TemplateDeviceAttrVO {



    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "通信编码")
    private String code;


}
