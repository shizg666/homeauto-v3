package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDeviceActionVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@ApiModel(value="JZSceneDetailVO", description="场景详情")
public class JZSceneDetailVO {

    @ApiModelProperty(value = "场景名称")
    private String name;

    @ApiModelProperty(value = "场景图标")
    private String icon;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "房间配置")
    List<JZSceneDetailRoomDeviceVO> rooms;

    @ApiModelProperty(value = "暖通设备配置")
    JzSceneDetailDeviceVO systemDevice;

//    @ApiModelProperty(value = "关联的设备动作")
//    List<HouseSceneActionDescVO> deviceConfigs;

}
