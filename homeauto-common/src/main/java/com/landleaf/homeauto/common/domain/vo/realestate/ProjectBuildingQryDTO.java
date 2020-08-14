package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@ApiModel(value="ProjectBuildingQryDTO", description="ProjectBuildingQryDTO")
public class ProjectBuildingQryDTO extends BaseQry {



    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "楼盘id")
    private String realestateId;

    @ApiModelProperty(value = "名称")
    private String name;




}
