package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.dto.SceneActionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自由方舟APP全屋场景详情视图对象
 *
 * @author Yujiumin
 * @version 2020/9/1
 */
@Data
@NoArgsConstructor
@ApiModel("自由方舟APP场景详情视图对象")
public class NonSmartSceneDetailVO {

    @ApiModelProperty("场景配置ID")
    private String sceneConfigId;

    @ApiModelProperty(value = "场景名称", required = true)
    private String sceneName;

    @ApiModelProperty(value = "场景图片", required = true)
    private String picUrl;

    @ApiModelProperty(value = "是否为常用场景", required = true)
    private Integer commonUse;

    @ApiModelProperty(value = "场景配置列表", required = true)
    private List<SceneActionVO> sceneActions;

}
