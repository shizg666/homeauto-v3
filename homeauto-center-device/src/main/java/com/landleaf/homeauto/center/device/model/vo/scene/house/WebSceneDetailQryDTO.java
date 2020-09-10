package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="WebSceneDetailQryDTO", description="场景详情查询入参")
public class WebSceneDetailQryDTO {
    @ApiModelProperty(value = "场景主键id")
    private String id;

    @ApiModelProperty(value = "户型id")
    private String houseTemplateId;



}
