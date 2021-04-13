package com.landleaf.homeauto.center.device.model.domain.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProjectHouseTemplate对象", description="项目户型表")
public class ProjectHouseTemplate extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "户型名称")
    private String name;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "户型面积")
    private String area;

    @ApiModelProperty(value = "类型  1单楼层 2多楼层")
    private Integer type;


}
