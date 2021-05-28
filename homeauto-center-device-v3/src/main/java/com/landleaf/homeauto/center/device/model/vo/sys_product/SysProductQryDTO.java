package com.landleaf.homeauto.center.device.model.vo.sys_product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 系统产品表
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysProductQryDTO", description="系统产品表")
public class SysProductQryDTO {

    @ApiModelProperty(value = "系统产品名称")
    private String name;


}
