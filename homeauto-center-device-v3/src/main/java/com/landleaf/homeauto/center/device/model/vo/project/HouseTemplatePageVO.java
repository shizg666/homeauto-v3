package com.landleaf.homeauto.center.device.model.vo.project;

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
@ApiModel(value="HouseTemplatePageVO", description="户型列表分页对象")
public class HouseTemplatePageVO {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "户型名称")
    private String name;

    @ApiModelProperty(value = "户型面积")
    private String area;

    @ApiModelProperty(value = "类型  1单楼层 2多楼层")
    private Integer type;

    @ApiModelProperty(value = "房间数")
    private int roomNum;

}
