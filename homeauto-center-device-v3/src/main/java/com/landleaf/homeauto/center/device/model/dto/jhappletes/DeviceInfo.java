package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName DeviceInfo
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/30
 * @Version V1.0
 **/
@Data
@ApiModel(value="DeviceInfo", description="设备信息")
public class DeviceInfo {
    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备属性")
    private List<JZDeviceAttrDataVO> attrs;
}
