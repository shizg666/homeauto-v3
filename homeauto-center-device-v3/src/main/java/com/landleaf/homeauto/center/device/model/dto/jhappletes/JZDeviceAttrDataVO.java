package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName JZDeviceAttrDataVO
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/30
 * @Version V1.0
 **/
@Data
@ApiModel(value="JZDeviceAttrDataVO", description="设备属性信息")
public class JZDeviceAttrDataVO {
    @ApiModelProperty("属性id")
    private Long attrId;

    @ApiModelProperty("属性code")
    private String code;

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("当前值")
    private String currentVal;

    /**
     * {@link com.landleaf.homeauto.common.enums.category.AttributeTypeEnum}
     */
    @ApiModelProperty("属性类型 1 多选 2值域")
    private Integer type;

    @ApiModelProperty("属性范围对象 type=2 值域有值")
    private AttributeScopeVO scopeVO;

    @ApiModelProperty(value = "属性可选值 type=1 有值")
    private List<SceneDeviceAttributeInfoVO> infos;
}
