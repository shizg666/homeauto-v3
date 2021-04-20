package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="HouseSceneDTO", description="户型场景信息")
public class HouseSceneDTO {
    @ApiModelProperty(value = "场景主键id 修改必传")
    private Long id;

    @NotEmpty(message = "场景名称不能为空")
    @ApiModelProperty(value = "场景名称")
    private String name;

    @ApiModelProperty(value = "户型id")
    private Long houseTemplateId;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "场景类型1 全屋场景 默认全屋场景")
    private Integer type = 1;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "是否可修改 1是 0否 ")
    private Integer updateFlag;

    @ApiModelProperty(value = "场景图标")
    private String icon;


    @ApiModelProperty(value = "设备动作 ")
    List<HouseSceneInfoDTO> deviceActions;



//    @ApiModelProperty(value = "是否有暖通1是 0否  ")
//    private Integer hvacFlag;

//    @ApiModelProperty(value = "场景编号")
//    private String sceneNo;






}
