package com.landleaf.homeauto.common.domain.dto.device.status;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 设备基本状态表(暖通、数值、在线离线标记)
 */
@Data
@NoArgsConstructor
@ApiModel("设备基本状态表(暖通、数值、在线离线标记)")
public class ScreenDeviceInfoStatusDTO {
    @ApiModelProperty(value = "家庭ID")
    private Long familyId;

    @ApiModelProperty(value = "设备编码")
    private String deviceSn;

    @ApiModelProperty(value = "设备ID")
    private Long deviceId;

    @ApiModelProperty(value = "暖通故障标记")
    private Integer havcFaultFlag;

    @ApiModelProperty(value = "数值故障标记")
    private Integer valueFaultFlag;

    @ApiModelProperty(value = "在线离线标记")
    private Integer onlineFlag;

    private String categoryCode;

    private String productCode;

    /**
     * type 1:二进制，2：数值，3：通信
     * Map<type,Map<code,value>>
     */
    @ApiModelProperty(value = "当前故障详情")
    private Map<Integer,Map<String,String>> currentDetailMap;


}
