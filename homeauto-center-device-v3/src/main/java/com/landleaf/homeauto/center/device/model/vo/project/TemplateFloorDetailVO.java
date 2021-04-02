package com.landleaf.homeauto.center.device.model.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 户型楼层表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateFloorDetailVO", description="户型楼层详情")
public class TemplateFloorDetailVO {

    @ApiModelProperty(value = "楼层id")
    private String id;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "房间信息")
    private List<TemplateRoomDetailVO> rooms;

}
