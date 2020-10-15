package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 切换家庭时展示的视图层对象
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP切换家庭视图对象")
public class FamilyCheckoutVO {

    @ApiModelProperty("天气信息")
    private FamilyWeatherVO weather;

    @ApiModelProperty("常用场景信息")
    private List<FamilySceneVO> commonSceneList;

    @ApiModelProperty("常用设备信息")
    private List<FamilyDeviceVO> commonDeviceList;
}
