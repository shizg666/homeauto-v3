package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectQryDTO", description="项目查询对象")
public class ProjectQryDTO extends BaseQry {

    @ApiModelProperty(value = "开发商code")
    private String developerCode;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "楼盘id")
    private String realestateId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "楼盘名称")
    private String realestateName;

    @ApiModelProperty(value = "用户paths ")
    private List<String> paths;

}
