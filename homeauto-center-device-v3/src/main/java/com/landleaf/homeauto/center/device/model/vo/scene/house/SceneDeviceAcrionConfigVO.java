package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="属性信息对象")
public class SceneDeviceAcrionConfigVO {

//    @ApiModelProperty(value = "属性id")
//    private String id;

    @ApiModelProperty(value = "属性名称")
    private String  name;

    @ApiModelProperty(value = "属性code")
    private String  code;

    @ApiModelProperty(value = "1 多选 2 值域")
    private Integer type;

    @ApiModelProperty(value = "选中的值(为null则该属性没配置)")
    private String selectVal;

//    @ApiModelProperty(value = "运算符: 0-等于 1-大于 2大于等于 -1-小于 -2-小于等于")
//    private Integer operate;

    @ApiModelProperty(value = "type=1 多选 有值")
    List<AttributeInfoVO> options;

    @ApiModelProperty(value = "type=2  值域 有值")
    private AtrributeRangeVO rangeVO;
}
