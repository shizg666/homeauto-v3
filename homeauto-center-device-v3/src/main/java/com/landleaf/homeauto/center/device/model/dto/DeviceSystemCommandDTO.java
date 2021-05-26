package com.landleaf.homeauto.center.device.model.dto;

import com.landleaf.homeauto.common.domain.dto.AppDeviceAttributeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("系统产品(暖通)控制数据对象")
public class DeviceSystemCommandDTO {
    @ApiModelProperty(value = "家庭ID", required = true)
    private String familyId;
    @ApiModelProperty("控制参数")
    private List<AppDeviceAttributeDTO> data;

}
