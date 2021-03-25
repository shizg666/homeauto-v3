package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 户式化APP家庭场景信息
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭场景信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilySceneVO {

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

    @ApiModelProperty("场景索引(场景索引建议使用这个字段)")
    private Integer sceneIndex;

    @ApiModelProperty("场景联动设备信息")
    private List<FamilyDeviceVO> linkageDeviceList;

    @ApiModelProperty("是否选中(用于定时场景)")
    private Integer checked;

    @ApiModelProperty("是否是默认场景")
    private Integer defaultFlag;

}
