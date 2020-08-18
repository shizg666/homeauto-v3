package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 定时场景详情视图对象
 *
 * @author Yujiumin
 * @version 2020/8/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("定时场景详情视图对象")
public class TimingSceneDetailVO {

    @ApiModelProperty("定时场景ID")
    private String timingId;

    @ApiModelProperty("触发时间")
    private String execTime;

    @ApiModelProperty(value = "重复类型", notes = "")
    private Integer repeatType;

    @ApiModelProperty("重复类型值")
    private String repeatValue;

    @ApiModelProperty(value = "是否跳过节假日", notes = "0:否, 1:是")
    private Integer skipHoliday;

    @ApiModelProperty("场景列表")
    private List<SceneVO> scenes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("定时场景内待选择的场景")
    public static class SceneVO {

        @ApiModelProperty("场景ID")
        private String sceneId;

        @ApiModelProperty("场景名称")
        private String sceneName;

        @ApiModelProperty("场景图标")
        private String sceneIcon;

        @ApiModelProperty("是否选中")
        private Integer checked;

    }

}
