package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@ApiModel(value="WebSceneDetailDTO", description="场景详情")
public class WebSceneDetailDTO {
    @ApiModelProperty(value = "场景主键id 修改必传")
    private String id;

    @ApiModelProperty(value = "场景名称")
    private String name;

    @ApiModelProperty(value = "场景图标")
    private String icon;

    @ApiModelProperty(value = "是否有暖通1是 0否  ")
    private Integer hvacFlag;

    @ApiModelProperty(value = "非暖通设备动作 ")
    List<WebSceneDetailDeviceActionVO> deviceActions;

    @ApiModelProperty(value = "暖通设备动作")
    private List<WebSceneDetailHvacConfigVO> hvacConfigDTOs;






}
