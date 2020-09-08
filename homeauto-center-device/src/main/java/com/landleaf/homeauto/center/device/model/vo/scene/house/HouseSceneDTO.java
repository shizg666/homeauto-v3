package com.landleaf.homeauto.center.device.model.vo.scene.house;

import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceActionInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneHvacActionDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneHvacConfigDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="HouseSceneDTO", description="户型场景信息")
public class HouseSceneDTO {
    @ApiModelProperty(value = "场景主键id 修改必传")
    private String id;

    @NotEmpty(message = "场景名称不能为空")
    @ApiModelProperty(value = "场景名称")
    private String name;

    @ApiModelProperty(value = "户型id")
    private String houseTemplateId;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "场景类型1 全屋场景 2 智能场景")
    private Integer type;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "app是否可修改 1是 0否 ")
    private Integer updateFlagApp;

    @ApiModelProperty(value = "场景图标")
    private String icon;

    @ApiModelProperty(value = "大屏是否可修改 1是 0否 ")
    private Integer defaultFlagScreen;

    @ApiModelProperty(value = "是否有暖通1是 0否  ")
    private Integer hvacFlag;


    @ApiModelProperty(value = "非暖通设备动作 ")
    List<SceneDeviceActionInfoDTO> deviceActions;

    @ApiModelProperty(value = "暖通设备动作")
    private List<SceneHvacConfigDTO> hvacConfigDTOs;




}
