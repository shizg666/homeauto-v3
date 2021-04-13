package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="属性对应的属性值对象", description="")
@Builder
public class AttributeInfoVO {


//    @ApiModelProperty(value = "属性多态id")
//    private String id;

    @ApiModelProperty(value = "属性值名称")
    private String  name;

    @ApiModelProperty(value = "属性值")
    private String  val;

//    @ApiModelProperty(value = "选中的值(为null则该属性没配置)")
//    private String selectVal;


}
