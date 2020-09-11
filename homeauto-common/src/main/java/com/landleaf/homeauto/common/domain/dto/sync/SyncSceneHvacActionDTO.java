package com.landleaf.homeauto.common.domain.dto.sync;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/8/29 10:06
 * @description: 邮箱信息
 */
@Data
@ToString
@ApiModel(value="SyncSceneHvacActionDTO", description="场景暖通动作同步")
public class SyncSceneHvacActionDTO {

    @ApiModelProperty("属性配置")
    private List<SyncSceneActionDTO> attrs;

    @ApiModelProperty("面板配置信息")
    private List<SyncScenePanelActionDTO> temPanel;



}
