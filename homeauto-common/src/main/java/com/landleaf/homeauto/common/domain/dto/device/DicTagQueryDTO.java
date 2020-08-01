package com.landleaf.homeauto.common.domain.dto.device;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("字典表标签查询条件")
public class DicTagQueryDTO extends BaseQry {

    @ApiModelProperty("字典标签名")
    private String name;

    @ApiModelProperty("是否为管理员")
    private Boolean isAdmin;

    @ApiModelProperty("字典码")
    private String dicCode;

}


