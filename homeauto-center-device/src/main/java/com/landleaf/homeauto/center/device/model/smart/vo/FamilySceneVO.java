package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 户式化APP家庭场景信息
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭场景信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilySceneVO {

    @ApiModelProperty("场景ID")
    private String sceneId;

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景图标")
    private String sceneIcon;

    @ApiModelProperty("场景索引(场景索引建议使用这个字段)")
    private Integer sceneIndex;

    @Deprecated
    @ApiModelProperty("场景索引(兼容旧接口)")
    private Integer index;

}
