package com.landleaf.homeauto.center.device.model.vo.scene.house;

import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDeviceActionVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneActionDescVO;
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
@ApiModel(value="WebSceneDetailVO", description="场景详情")
public class WebSceneDetailVO {
    @ApiModelProperty(value = "场景主键id 修改必传")
    private String id;

    @ApiModelProperty(value = "场景名称")
    private String name;

    @ApiModelProperty(value = "场景图标")
    private String icon;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "设备配置")
    List<WebSceneDetailDeviceActionVO> deviceConfigs;

//    @ApiModelProperty(value = "关联的设备动作")
//    List<HouseSceneActionDescVO> deviceConfigs;

}
