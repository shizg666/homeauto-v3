package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 家庭常用设备和场景视图对象
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("家庭常用设备和场景视图对象")
public class IndexVO {

    @ApiModelProperty("天气信息")
    private WeatherVO weather;

    @ApiModelProperty("常用场景列表")
    private List<FamilySceneVO> scenes;

    @ApiModelProperty("常用设备列表")
    private List<FamilyDeviceVO> devices;

}
