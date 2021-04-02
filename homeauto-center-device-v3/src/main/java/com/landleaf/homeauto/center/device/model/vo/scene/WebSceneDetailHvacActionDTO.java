package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="WebSceneDetailHvacActionDTO", description="查看-场景暖通设备动作信息")
public class WebSceneDetailHvacActionDTO {

    @ApiModelProperty(value = "模式值")
    private String modeVal;

    @ApiModelProperty(value = "风量值")
    private String windVal;

    @ApiModelProperty(value = "开关值")
    private String switchVal;

    @ApiModelProperty(value = "温度值")
    private String temperatureVal;

    @ApiModelProperty(value = "是否是分室 0否1是")
    private Integer roomFlag;

    @ApiModelProperty(value = "面板动作")
    private List<WebSceneHvacPanelActionDTO> panelActionDTOs;



}
