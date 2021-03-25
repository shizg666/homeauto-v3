package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/10/12
 */
@Data
@ApiModel("场景图标更新信息DTO")
public class SceneIconUpdateDTO {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("场景图标")
    private String sceneIcon;

    @ApiModelProperty("场景图标类型")
    private Integer sceneIconType;

}
