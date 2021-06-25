package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 场景联动关备属性表
 * </p>
 *
 * @author lokiy
 * @since 2019-08-22
 */
@Data
@Accessors(chain = true)
@ApiModel(value="JZSceneDeviceDTO", description="场景设备信息")
public class JZSceneDeviceDTO {

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    @ApiModelProperty(value = "产品编号")
    private String productCode;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "设备动作配置")
    List<JZSceneDeviceActionDTO> actions;

}
