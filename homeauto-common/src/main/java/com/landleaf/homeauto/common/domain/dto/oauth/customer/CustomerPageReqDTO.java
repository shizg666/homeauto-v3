package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户列表分页请求DTO
 *
 * @author wenyilu*/
@Data
@ApiModel(value = "客户列表分页请求DTO", description = "客户列表分页请求DTO")
public class CustomerPageReqDTO extends BaseQry {


    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "是否绑定工程")
    private Integer bindFlag;


}
