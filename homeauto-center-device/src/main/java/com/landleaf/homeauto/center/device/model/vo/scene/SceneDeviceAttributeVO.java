package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="SceneDeviceAttributeVO", description="场景设备属性信息")
public class SceneDeviceAttributeVO {

    @ApiModelProperty("属性id")
    private String id;

    @ApiModelProperty("属性code")
    private String code;

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("属性类型")
    private Integer type;

    @ApiModelProperty("属性范围对象")
    private AttributeScopeVO scopeVO;

    @ApiModelProperty("产品id")
    private String productId;


    @ApiModelProperty(value = "属性可选值")
    private List<SceneDeviceAttributeInfoVO> infos;



}
