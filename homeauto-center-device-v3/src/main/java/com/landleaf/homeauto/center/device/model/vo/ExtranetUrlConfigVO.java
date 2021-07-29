package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 本地数采外网URL配置
 * </p>
 *
 * @author lokiy
 * @since 2021-07-28
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ExtranetUrlConfigVO", description="本地数采外网URL配置")
public class ExtranetUrlConfigVO {


    @ApiModelProperty(value = "0 离线 1在线")
    private Integer status;

    @NotEmpty(message = "url不能为空")
    @ApiModelProperty(value = "数据源外网访问url")
    private String url;

}
