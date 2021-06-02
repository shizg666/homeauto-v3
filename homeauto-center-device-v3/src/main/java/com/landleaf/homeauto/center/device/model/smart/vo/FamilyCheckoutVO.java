package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyCheckoutVO {

    @ApiModelProperty("天气信息")
    private FamilyWeatherVO weather;

    @ApiModelProperty("常用场景信息")
    private List<FamilySceneVO> commonSceneList;

    @ApiModelProperty("首页暖通与房间按钮展示控制--0:都没有，1：只有暖通按钮，2：只有房间按钮，3：暖通与房间按钮都有")
    private Integer buttonControlFlag;

    @ApiModelProperty("常用设备信息")
    private List<FamilyDeviceVO> commonDeviceList;



}
