package com.landleaf.homeauto.center.device.model.vo.scene.house;

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
@ApiModel(value="HouseSceneInfoDTO", description="户型场景动作添加")
public class HouseSceneInfoDTO {

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

//    @ApiModelProperty(value = "情景id")
//    private Long sceneId;
//
//    @ApiModelProperty(value = "户型id")
//    private Long templateId;

    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    @ApiModelProperty(value = "产品编号")
    private String productCode;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "动作列表")
    List<HouseSceneActionConfigDTO> actions;

}
