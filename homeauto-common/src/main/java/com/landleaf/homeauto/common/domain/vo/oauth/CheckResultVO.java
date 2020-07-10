package com.landleaf.homeauto.common.domain.vo.oauth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 工程用户关联表
 * </p>
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CheckResultVO", description="CheckResultVO")
public class CheckResultVO {

    @ApiModelProperty(value = "工程id")
    private boolean checkFlag;

    @ApiModelProperty(value = "原因")
    private String message;



}
