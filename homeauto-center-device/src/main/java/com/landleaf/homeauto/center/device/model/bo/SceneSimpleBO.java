package com.landleaf.homeauto.center.device.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/8/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("定时场景内待选择的场景")
public class SceneSimpleBO {

    @ApiModelProperty("场景ID")
    private String sceneId;

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景图标")
    private String sceneIcon;

    @ApiModelProperty("是否选中")
    private Integer checked;

}
