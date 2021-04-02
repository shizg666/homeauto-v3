package com.landleaf.homeauto.center.device.model.vo.project;

import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseScenePageVO;
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
@ApiModel(value="HouseTemplateDetailVO", description="户型详情")
public class HouseTemplateDetailVO {

    @ApiModelProperty(value = "楼层信息")
    private List<TemplateFloorDetailVO> floors;

    @ApiModelProperty(value = "设备列表")
    private List<TemplateDevicePageVO> devices;

    @ApiModelProperty(value = "场景列表")
    private List<HouseScenePageVO> scenes;



}
