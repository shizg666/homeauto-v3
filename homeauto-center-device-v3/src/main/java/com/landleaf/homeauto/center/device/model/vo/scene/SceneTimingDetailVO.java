package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.bo.SceneSimpleBO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
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
public class SceneTimingDetailVO {

    @ApiModelProperty("定时场景ID")
    private String timingId;

    @ApiModelProperty("触发时间")
    private String executeTime;

    @ApiModelProperty("重复类型 (1:不重复, 2:自定义周, 3:自定义日期)")
    private Integer repeatType;

    @ApiModelProperty("重复类型值")
    private String repeatValue;

    @ApiModelProperty(value = "是否跳过节假日 (0:否, 1:是)")
    private Integer skipHoliday;

    @ApiModelProperty(value = "被选择的场景ID")
    private String sceneId;

    @ApiModelProperty("是否启用")
    private Integer enabled;

    @ApiModelProperty("场景列表")
    private List<FamilySceneVO> scenes;

}
