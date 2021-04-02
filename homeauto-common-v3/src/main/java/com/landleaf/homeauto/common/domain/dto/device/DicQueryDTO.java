package com.landleaf.homeauto.common.domain.dto.device;

import com.landleaf.homeauto.common.domain.Pagination;
import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Yujiumin
 * @version 2020/07/13
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("字典查询条件")
public class DicQueryDTO extends BaseQry {

    @ApiModelProperty("字典名称")
    private String name;

}
