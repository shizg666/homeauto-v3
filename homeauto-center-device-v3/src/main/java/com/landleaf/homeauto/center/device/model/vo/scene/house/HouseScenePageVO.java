package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="HouseScenePageVO", description="场景分页对象")
public class HouseScenePageVO {

    @ApiModelProperty("场景id")
    private Long id;

    @ApiModelProperty(value = "情景名称")
    private String name;

    @ApiModelProperty(value = "户型id")
    private Long houseTemplateId;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "是否可修改 1是 0否 ")
    private Integer updateFlag;




}
