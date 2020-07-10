package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * app客户修改头像请求 DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "CustomerUpdateAvatarReqDTO", description = "app客户修改头像请求 DTO")
public class CustomerUpdateAvatarReqDTO {

    @ApiModelProperty(value = "头像", required = true)
    private String avatar;

}
