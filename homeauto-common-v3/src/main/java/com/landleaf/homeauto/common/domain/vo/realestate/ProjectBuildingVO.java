package com.landleaf.homeauto.common.domain.vo.realestate;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectBuildingVO", description="ProjectBuildingVO")
public class ProjectBuildingVO {


    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "楼栋号")
    private String code;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProjectBuildingUnitVO> units;
}
