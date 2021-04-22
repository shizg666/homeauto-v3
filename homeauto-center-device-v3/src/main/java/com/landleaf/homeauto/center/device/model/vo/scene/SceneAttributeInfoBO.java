package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="SceneAttributeInfoBO", description="SceneAttributeInfoBO")
public class SceneAttributeInfoBO {

    @ApiModelProperty("属性code")
    @Ignore
    private Long id;

    @ApiModelProperty("属性code")
    private String attributeCode;

    @ApiModelProperty("属性当前值")
    private String val;




}
