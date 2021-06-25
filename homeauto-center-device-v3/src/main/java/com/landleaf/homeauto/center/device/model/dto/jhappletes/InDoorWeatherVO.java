package com.landleaf.homeauto.center.device.model.dto.jhappletes;

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
@ApiModel("家庭所在城市天气信息")
public class InDoorWeatherVO {

    @ApiModelProperty("温度")
    private String temp;

    @ApiModelProperty("PM2.5指数")
    private String pm25;

    @ApiModelProperty("湿度")
    private String humidity;

    @ApiModelProperty("二氧化碳")
    private String co;


}
