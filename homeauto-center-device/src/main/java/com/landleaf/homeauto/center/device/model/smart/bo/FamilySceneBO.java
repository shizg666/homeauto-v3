package com.landleaf.homeauto.center.device.model.smart.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭场景业务对象")
public class FamilySceneBO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("户型ID")
    private String templateId;

    @ApiModelProperty("场景ID")
    private String sceneId;

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景图标")
    private String sceneIcon;

    @ApiModelProperty("场景索引")
    private Integer sceneIndex;

    @ApiModelProperty("场景下的设备信息")
    private List<FamilyDeviceBO> deviceBOList;

}
