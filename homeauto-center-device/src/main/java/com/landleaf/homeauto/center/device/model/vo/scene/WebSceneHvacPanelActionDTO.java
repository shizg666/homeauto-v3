package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 场景暖通面板动作
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Data
@Accessors(chain = true)
@ApiModel(value="WebSceneHvacPanelActionDTO", description="查看-场景暖通面板动作")
public class WebSceneHvacPanelActionDTO {


    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "开关值")
    private String switchVal;

    @ApiModelProperty(value = "温度值")
    private String temperatureVal;




}
