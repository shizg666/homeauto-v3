package com.landleaf.homeauto.center.device.model.bo.screen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 项目信息
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenProjectBO", description = "项目信息")
public class ScreenProjectBO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "项目ID")
    private Long projectId;


}
