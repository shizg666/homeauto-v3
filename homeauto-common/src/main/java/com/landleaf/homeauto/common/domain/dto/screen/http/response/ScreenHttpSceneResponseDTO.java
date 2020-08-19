package com.landleaf.homeauto.common.domain.dto.screen.http.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 户式化场景请求返回
 *
 * @author wenyilu
 */
@Data
public class ScreenHttpSceneResponseDTO {

    @ApiModelProperty(value = "场景ID")
    private String sceneId;

    @ApiModelProperty(value = "情景名称")
    private String sceneName;

    @ApiModelProperty(value = "家庭编码")
    private String familyCode;

    @ApiModelProperty(value = "场景类型1 全屋场景 2 智能场景")
    private Integer sceneType;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "大屏是否可修改 1是 0否 ")
    private Integer defaultFlagScreen;

    @ApiModelProperty(value = "app是否可修改 1是 0否 ")
    private Integer updateFlagApp;

    @ApiModelProperty(value = "场景图标")
    private String sceneIcon;


    /**
     * 场景动作
     */
    List<ScreenHttpSceneActionDTO> actions;
}
