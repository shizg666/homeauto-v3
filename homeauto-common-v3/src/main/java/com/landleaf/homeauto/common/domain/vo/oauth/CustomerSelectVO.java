package com.landleaf.homeauto.common.domain.vo.oauth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 客户下拉列表
 * </p>
 *
 * @author wyl
 */
@Data
@ApiModel(value = "CustomerSelectVO", description = "根据用户名或手机号查询返回VO")
public class CustomerSelectVO implements Serializable {

    private static final long serialVersionUID = -7462091749321521569L;

    @ApiModelProperty(value = "客户ID")
    private String userId;

    @ApiModelProperty(value = "客户手机号")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    private String name;


}
