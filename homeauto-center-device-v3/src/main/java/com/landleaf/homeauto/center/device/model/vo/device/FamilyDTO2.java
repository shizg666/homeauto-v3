package com.landleaf.homeauto.center.device.model.vo.device;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 家庭设备管理查询请求体
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "FamilyDTO2", description = "家庭设备管理查询请求体")
public class FamilyDTO2  {

    @ApiModelProperty(value = "楼栋号",required = true)
    private String buildingCode;
    @ApiModelProperty("单元")
    private String unitCode;
    @ApiModelProperty("门牌号")
    private String doorplate;


}
