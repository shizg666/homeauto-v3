package com.landleaf.homeauto.center.device.model.vo.scene.house;

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
@ApiModel(value="HouseSceneDeviceActionBO", description="户型场景设备动作")
public class HouseSceneDeviceActionBO {

//    @ApiModelProperty(value = "关联的id")
//    private String id;

    @ApiModelProperty(value = "属性值")
    private String val;

    @ApiModelProperty(value = "属性值名称")
    private String valName;

    @ApiModelProperty(value = "属性名称")
    private String  name;

    @ApiModelProperty(value = "属性类型")
    private Integer  attrType;



}
