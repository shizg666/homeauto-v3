package com.landleaf.homeauto.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭常用场景
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("家庭常用场景")
public class FamilyCommonSceneVO {

    @ApiModelProperty("场景ID")
    private String sceneId;

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景图标")
    private String sceneIcon;

    @ApiModelProperty("索引值")
    private Integer index;


}
