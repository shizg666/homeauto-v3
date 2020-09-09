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
@ApiModel(value="SceneDeviceAttributeInfoVO", description="场景设备属性值信息")
public class SceneDeviceAttributeInfoVO {



    @ApiModelProperty("属性code")
    private String code;

    @ApiModelProperty("属性名称")
    private String name;




}
