package com.landleaf.homeauto.center.device.model.dto.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/8/14 12:00
 * @description:
 */
@Data
@ToString
@ApiModel("地址相关信息")
@EqualsAndHashCode(of = "code")
public class ShAddressDTO {

    @ApiModelProperty( value = "地址 路径", required = true)
    private String path;

    @ApiModelProperty("地址名称")
    private String name;


}
