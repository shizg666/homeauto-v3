package com.landleaf.homeauto.center.device.model.bo.screen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenTemplateDeviceBO", description = "户型设备信息")
public class ScreenTemplateDeviceBO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备编号")
    private String deviceSn;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "房间ID")
    private String roomId;

    @ApiModelProperty(value = "户型ID")
    private String houseTemplateId;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;


}
