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
@ApiModel(value="OutDoorWeatherVO", description="家庭所在城市天气信息")
public class OutDoorWeatherVO {

    @ApiModelProperty("天气情况")
    private String weatherStatus;

    @ApiModelProperty("当前气温")
    private String temp;

    @ApiModelProperty("PM2.5指数")
    private String pm25;

    @ApiModelProperty("湿度")
    private String humidity;

    @ApiModelProperty("天气图片")
    private String picUrl;
}
