package com.landleaf.homeauto.common.domain.vo.realestate;

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
@Accessors(chain = true)
@ApiModel(value="ProjectBuildingUnitVO", description="ProjectBuildingUnitVO")
public class ProjectBuildingUnitVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单元id")
    private String id;

    @ApiModelProperty(value = "单元号")
    private String code;


}
