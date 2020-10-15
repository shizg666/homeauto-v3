package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 家庭所在城市天气信息
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭所在城市天气信息")
public class FamilyWeatherVO {

    @ApiModelProperty("空气质量")
    private String airQuality;

    @ApiModelProperty("天气情况")
    private String weatherStatus;

    @ApiModelProperty("当前温度")
    private String temp;

    @ApiModelProperty("最低温度")
    private String minTemp;

    @ApiModelProperty("最高温度")
    private String maxTemp;

    @ApiModelProperty("天气图片")
    private String picUrl;
}
