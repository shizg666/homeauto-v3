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

    @ApiModelProperty(value = "APP类型 1-andriod 2-ios",notes = "APP类型 1-andriod 2-ios")
    private Integer appType;

    @ApiModelProperty(value = "强制标识(1:强制)",notes = "强制标识(1:强制,0:非强制)")
    private Integer forceFlag;

    @ApiModelProperty(value = "所属app(smart,non-smart)",notes = "所属app(智能家居:smart,自由方舟:non-smart)")
    private String belongApp;
}
