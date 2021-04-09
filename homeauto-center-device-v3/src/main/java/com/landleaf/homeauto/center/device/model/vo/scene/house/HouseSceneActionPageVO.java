package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 情景表
 * </p>
 *
 * @author lokiy
 * @since 2019-08-19
 */
@Data
@Accessors(chain = true)
@ApiModel(value="HouseSceneActionPageVO", description="情景表")
public class HouseSceneActionPageVO {

    @ApiModelProperty(value = "情景名称")
    private String name;

    @ApiModelProperty(value = "情景id")
    private String sceneId;

//    @ApiModelProperty(value = "房间id")
//    private String roomId;

    @ApiModelProperty(value = "是否可修改 1是 0否")
    private Integer updateFlag;

    @ApiModelProperty(value = "关联的设备属性信息列表")
    List<HouseSceneActionPageDetailVO> actionDesc;


}
