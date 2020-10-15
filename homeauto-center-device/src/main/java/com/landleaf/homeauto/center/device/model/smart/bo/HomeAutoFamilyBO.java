package com.landleaf.homeauto.center.device.model.smart.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 户式化APP家庭业务对象
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭信息业务对象")
public class HomeAutoFamilyBO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("家庭编码")
    private String familyCode;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("门牌号")
    private String familyNumber;

    @ApiModelProperty("户型名称")
    private String templateName;

    @ApiModelProperty("单元ID")
    private String unitId;

    @ApiModelProperty("楼栋ID")
    private String buildingId;

    @ApiModelProperty("项目ID")
    private String projectId;

    @ApiModelProperty("楼盘ID")
    private String realestateId;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("天气编码")
    private String weatherCode;

}
