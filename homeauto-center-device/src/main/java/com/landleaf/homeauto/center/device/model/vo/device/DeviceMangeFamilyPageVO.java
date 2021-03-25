package com.landleaf.homeauto.center.device.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 家庭设备
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DeviceMangeFamilyPageVO", description = "DeviceMangeFamilyPageVO")
public class DeviceMangeFamilyPageVO {

    @ApiModelProperty("楼盘名称")
    private String realestateName;

    @ApiModelProperty("楼盘地址")
    private String address;

    @ApiModelProperty("项目项目")
    private String projectName;

    @ApiModelProperty("户型名称")
    private String templateName;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("家庭id")
    private String familyId;

    @ApiModelProperty("家庭code")
    private String familyCode;


}
