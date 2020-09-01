package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
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
public class AdapterHttpSaveOrUpdateSceneDTO extends AdapterMessageHttpDTO {

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
