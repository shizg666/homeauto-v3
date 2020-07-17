package com.landleaf.homeauto.common.domain.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author wenyilu
 */
@Data
@ApiModel("基础查询对象")
public class BaseQry {

    @ApiModelProperty(value = "每页的数量",required = true)
    @Min(value = 1, message = "当前页码不合法")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "当前页",required = true)
    @Min(value = 1, message = "每页展示数量不合法")
    private Integer pageNum = 1;

}
