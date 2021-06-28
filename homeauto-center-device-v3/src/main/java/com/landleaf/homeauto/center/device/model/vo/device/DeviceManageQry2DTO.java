package com.landleaf.homeauto.center.device.model.vo.device;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 家庭设备
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DeviceManageQry2DTO", description = "设别管理查询对象")
public class DeviceManageQry2DTO extends BaseQry {

    @ApiModelProperty(value = "家庭id",required = true)
    private Long familyId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("品类")
    private String categoryCode;


}
