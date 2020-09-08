package com.landleaf.homeauto.center.device.model.vo.scene;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="SceneHvacConfigDTO", description="场景暖通设备配置信息")
public class SceneHvacConfigDTO {

    @ApiModelProperty(value = "id主键")
    private String id;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "系统开关属性code")
    private String switchCode;

    @ApiModelProperty(value = "属性值")
    private String switchVal;

    @ApiModelProperty(value = "户型id")
    private String houseTemplateId;

    @ApiModelProperty(value = "场景id")
    private String sceneId;

    @ApiModelProperty(value = "暖通动作")
    private SceneHvacActionDTO hvacActionDTO;





}
