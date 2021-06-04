package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭设备信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDeviceSimpleVO {

    @ApiModelProperty("设备ID")
    private Long deviceId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备图标")
    private String deviceIcon;

    @ApiModelProperty("设备图片")
    private String deviceImage;

    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("品类编码")
    private String categoryCode;

    @ApiModelProperty("是否系统设备1：是")
    private Integer systemFlag;



}
