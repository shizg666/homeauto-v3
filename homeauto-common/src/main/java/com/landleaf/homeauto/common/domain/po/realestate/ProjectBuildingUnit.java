package com.landleaf.homeauto.common.domain.po.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 楼栋单元表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProjectBuildingUnit对象", description="楼栋单元表")
public class ProjectBuildingUnit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单元名称")
    private String name;

    @ApiModelProperty(value = "楼栋id")
    private String buildingId;

    @ApiModelProperty(value = "单元号")
    private String code;


}
