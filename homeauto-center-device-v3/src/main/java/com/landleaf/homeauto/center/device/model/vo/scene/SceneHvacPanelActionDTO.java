package com.landleaf.homeauto.center.device.model.vo.scene;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@ApiModel(value="SceneHvacPanelActionDTO", description="场景暖通面板动作")
public class SceneHvacPanelActionDTO {


    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "开关code")
    private String switchCode;

    @ApiModelProperty(value = "开关值")
    private String switchVal;

    @ApiModelProperty(value = "温度code")
    private String temperatureCode;

    @ApiModelProperty(value = "温度值")
    private String temperatureVal;

    @ApiModelProperty(value = "暖通模式配置id")
    private String hvacActionId;

    @ApiModelProperty(value = "户型id")
    private String houseTemplateId;


}
