package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 楼栋单元表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectBuildingUnitDTO", description="ProjectBuildingUnitDTO")
public class ProjectBuildingUnitDTO  {

    private static final long serialVersionUID = 5411083152722444160L;
    @ApiModelProperty(value = "主键(修改必传)")
    private String id;

    @ApiModelProperty(value = "楼栋id")
    private String buildingId;

    @ApiModelProperty(value = "单元号")
    private String code;

    @NotEmpty(message = "项目id必传")
    @ApiModelProperty(value = "项目id（必传）")
    private String projectId;


}
