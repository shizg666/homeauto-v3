package com.landleaf.homeauto.center.device.model.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
@Data
@NoArgsConstructor
@ApiModel("天气信息")
public class WeatherVO {

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
