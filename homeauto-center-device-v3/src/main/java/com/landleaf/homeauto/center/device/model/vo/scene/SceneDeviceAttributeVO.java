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
    private Long id;

    @ApiModelProperty("属性code")
    private String code;

    @ApiModelProperty("属性名称")
    private String name;

    /**
     * {@link com.landleaf.homeauto.common.enums.category.AttributeTypeEnum}
     */
    @ApiModelProperty("属性类型 1 多选 2值域")
    private Integer type;

    @ApiModelProperty("属性范围对象 type 1 值域有值")
    private AttributeScopeVO scopeVO;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty(value = "属性可选值")
    private List<SceneDeviceAttributeInfoVO> infos;



}
