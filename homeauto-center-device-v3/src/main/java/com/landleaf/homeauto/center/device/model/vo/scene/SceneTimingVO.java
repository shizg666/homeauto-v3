package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定时场景视图对象
 *
 * @author Yujiumin
 * @version 2020/8/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("定时场景视图对象")
public class SceneTimingVO {

    @ApiModelProperty("定时场景ID")
    private String timingId;

    @ApiModelProperty("场景触发时间")
    private String executeTime;

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景生效时间")
    private String workday;

    @ApiModelProperty("场景启用状况")
    private Integer enabled;

}
