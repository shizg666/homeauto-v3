package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="JZRoomDeviceStatusCategoryVO", description="房间品类设备状态")
public class JZRoomDeviceStatusCategoryVO {


    @ApiModelProperty(value = "系统设备信息 品类是面板的时候有值")
    private DeviceInfo systemDevice;

    @ApiModelProperty(value = "设备列表")
    private  List<DeviceInfo> devices;


}
