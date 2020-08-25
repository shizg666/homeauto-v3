package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/8/25
 */
@Data
@NoArgsConstructor
@ApiModel("环境参数")
public class EnvironmentVO {

    @ApiModelProperty("空气质量")
    private String aqi;

    @ApiModelProperty("温度")
    private String temperature;

    @ApiModelProperty("二氧化碳浓度")
    private String co2;

    @ApiModelProperty("PM2.5浓度")
    private String pm25;

    @ApiModelProperty("甲醛浓度")
    private String hcho;
}
