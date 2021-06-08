package com.landleaf.homeauto.common.domain.dto.device.status;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: HomeAutoFaultDeviceCurrentDTO
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/8
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeAutoFaultDeviceCurrentDTO {
    @ApiModelProperty(value = "家庭")
    private Long familyId;
    @ApiModelProperty(value = "设备")
    private Long deviceId;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "所属楼盘")
    private Long realestateId;

    @ApiModelProperty(value = "所属项目")
    private Long projectId;

    @ApiModelProperty(value = "故障编码")
    private String code;
    @ApiModelProperty(value = "故障值")
    private String value;
    @ApiModelProperty(value = "故障类型(1:二进制故障，2：数值异常，3：通信)")
    private Integer type;
}
