package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpSceneActionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenSaveOrUpdateNonSmartSceneDTO
 * @Description: 修改、新增自由方舟场景请求
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenHttpSaveOrUpdateNonSmartSceneDTO extends ScreenHttpRequestDTO {

    private String sceneId;

    @ApiModelProperty(value = "情景名称")
    private String name;

    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @ApiModelProperty(value = "场景类型1 全屋场景 2 智能场景")
    private Integer type;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "大屏是否可修改 1是 0否 ")
    private Integer defaultFlagScreen;

    @ApiModelProperty(value = "app是否可修改 1是 0否 ")
    private Integer updateFlagApp;

    @ApiModelProperty(value = "场景图标")
    private String icon;

    /**
     * 场景动作
     */
    List<ScreenHttpSceneActionDTO> actions;
}
