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
@ApiModel(value="SceneDeviceAttributeVO", description="查看场景费暖通设备设备属性信息")
public class WebSceneDetailAttributeVO {

    @ApiModelProperty("属性id")
    private String id;

    @ApiModelProperty("属性code")
    private String code;

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("属性类型")
    private Integer type;

    @ApiModelProperty("是否选中 0否 1是")
    private Integer selected;

    @ApiModelProperty("当前的选中的值")
    private String val;

    @ApiModelProperty("属性范围对象 type=2 值域有值")
    private AttributeScopeVO scopeVO;

    @ApiModelProperty(value = "属性可选值 type=1 多选有值 ")
    private List<SceneDeviceAttributeInfoVO> infos;



}
