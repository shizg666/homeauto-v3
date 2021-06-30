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
@ApiModel(value = "FamilyDeviceQryDTO", description = "家庭设备管理查询请求体")
public class FamilyDeviceQryDTO extends BaseQry {

    @ApiModelProperty(value = "楼盘ID",required = true)
    private Long realestateId;
    @ApiModelProperty(value = "项目ID",required = true)
    private Long projectId;
    @ApiModelProperty(value = "系统产品id",required = false)
    private Long sysProductId;

    @ApiModelProperty(value = "设备类别 0 普通设备 1系统子设备",required = false)
    private Integer systemFlag;

    @ApiModelProperty(value = "楼栋号",required = true)
    private String buildingCode;
    @ApiModelProperty("房屋名称")
    private String familyName;

    @ApiModelProperty(value = "房屋id",required = true)
    private Long familyId;

    @ApiModelProperty("设备名称")
    private String deviceName;
    @ApiModelProperty("设备号")
    private String deviceSn;


}
