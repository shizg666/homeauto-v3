package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="JzSceneDetailDeviceActionVO", description="场景设备动作配置")
public class JzSceneDetailDeviceActionVO {

    @ApiModelProperty("属性id")
    private String id;

    @ApiModelProperty("属性code")
    private String code;

    @ApiModelProperty("属性名称")
    private String name;

    /**
     * {@link com.landleaf.homeauto.common.enums.category.AttributeTypeEnum }
     */
    @ApiModelProperty("属性类型  1多选 2值域")
    private Integer type;

    @ApiModelProperty("是否配置 0否 1是 属性type=1 多选有值")
    private Integer selected;

    @ApiModelProperty("配置的值")
    private String val;

    @ApiModelProperty("属性范围对象 type=2 值域有值")
    private AttributeScopeVO scopeVO;

    @ApiModelProperty(value = "属性可选值 type=1 多选有值 ")
    private List<SceneDeviceAttributeInfoVO> infos;



}
