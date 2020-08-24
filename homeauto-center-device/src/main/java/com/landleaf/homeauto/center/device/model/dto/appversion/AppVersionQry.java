package com.landleaf.homeauto.center.device.model.dto.appversion;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wenyilu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("版本信息查询对象")
public class AppVersionQry extends BaseQry {

    @ApiModelProperty("app版本")
    private String version;

    @ApiModelProperty("APP类型 1-andriod 2-ios")
    private Integer appType;

    @ApiModelProperty("强制标识")
    private Integer forceFlag;
}
