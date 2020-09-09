package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="SceneHvacDeviceVO", description="场景暖通设备信息")
public class SceneHvacDeviceVO {

    @ApiModelProperty("类别code")
    private String categoyCode;

    @ApiModelProperty(value = "设备号")
    private String sn;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "风量下拉选择")
    private List<SelectedVO> windSpeeds;


}
