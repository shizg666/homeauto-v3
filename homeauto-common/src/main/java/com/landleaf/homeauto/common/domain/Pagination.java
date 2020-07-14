package com.landleaf.homeauto.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Yujiumin
 * @version 2020/07/13
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页信息")
public class Pagination {

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页显示的数量")
    private Integer pageSize;

}
