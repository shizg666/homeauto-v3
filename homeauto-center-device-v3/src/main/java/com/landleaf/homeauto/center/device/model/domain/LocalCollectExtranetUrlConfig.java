package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 本地数采外网URL配置
 * </p>
 *
 * @author lokiy
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="LocalCollectExtranetUrlConfig对象", description="本地数采外网URL配置")
public class LocalCollectExtranetUrlConfig extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "楼盘code")
    private String realestateCode;

    @ApiModelProperty(value = "数据源外网访问url")
    private String url;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改人")
    private String updateUser;


}
