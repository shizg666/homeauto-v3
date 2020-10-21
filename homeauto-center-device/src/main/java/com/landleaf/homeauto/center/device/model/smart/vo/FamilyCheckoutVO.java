package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyCheckoutVO {

    @ApiModelProperty("天气信息")
    private FamilyWeatherVO weather;

    @JsonInclude
    @ApiModelProperty("暖通设备信息")
    private FamilyDeviceVO hvacDevice;

    @ApiModelProperty("常用场景信息")
    private List<FamilySceneVO> commonSceneList;

    @ApiModelProperty("常用设备信息")
    private List<FamilyDeviceVO> commonDeviceList;

    @Deprecated
    @ApiModelProperty("常用场景列表(兼容旧接口)")
    private List<FamilySceneVO> scenes;

    @Deprecated
    @ApiModelProperty("常用设备列表(兼容旧接口)")
    private List<FamilyDeviceVO> devices;
}
