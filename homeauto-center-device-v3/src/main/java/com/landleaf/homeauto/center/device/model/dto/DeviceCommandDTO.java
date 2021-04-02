package com.landleaf.homeauto.center.device.model.dto;

import com.landleaf.homeauto.common.domain.dto.AppDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/31
 */
@Data
@NoArgsConstructor
@ApiModel("设备控制数据传输对象")
public class DeviceCommandDTO {
    @ApiModelProperty(value = "家庭ID",required = true)
    private String familyId;
    @ApiModelProperty(value = "设备ID",required = true)
    private String deviceId;
    @ApiModelProperty("控制参数")
    private List<AppDeviceAttributeDTO> data;

}
