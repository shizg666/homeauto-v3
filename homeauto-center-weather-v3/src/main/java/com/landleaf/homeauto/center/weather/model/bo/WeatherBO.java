package com.landleaf.homeauto.center.weather.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天气业务对象
 *
 * @author Yujiumin
 * @version 2020/8/18
 */
@Data
@NoArgsConstructor
@ApiModel("原始的天气信息")
public class WeatherBO {

    @ApiModelProperty("发布日期")
    private String date;

    @ApiModelProperty("星期")
    private String week;

    @ApiModelProperty("农历日历")
    private String calender;

    @ApiModelProperty("着装等级")
    private String dressLevel;

    @ApiModelProperty("风力等级")
    private String windLevel;

    @ApiModelProperty("当前气温")
    private String temp;

    @ApiModelProperty("最低气温")
    private String minTemp;

    @ApiModelProperty("最高气温")
    private String maxTemp;

    @ApiModelProperty("发布时间")
    private String updateTime;

    @ApiModelProperty("天气状况")
    private String weatherStatus;

    @ApiModelProperty("简易版日历时间")
    private String singleCalender;

    @ApiModelProperty("紫外线强度")
    private String uvLevel;

    @ApiModelProperty("天气图片")
    private String picUrl;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("城市URL")
    private String cityUrl;

    @ApiModelProperty("PM2.5指数")
    private String pm25;

    @ApiModelProperty("")
    private String newPicUrl;

    @ApiModelProperty("运动等级")
    private String sportLevel;

    @ApiModelProperty("湿度")
    private String humidity;

    @ApiModelProperty("风向")
    private String windDirection;

    @ApiModelProperty("感冒等级")
    private String coldLevel;

}
