package com.landleaf.homeauto.center.device.model.dto.protocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 协议属性信息
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value="DeviceAttrInfoCacheBO", description="设备属性信息缓存")
public class DeviceAttrInfoCacheBO {

    @ApiModelProperty(value = "属性主键id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "通信编码")
    private String code;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "值类型")
    private Integer valueType;



}
