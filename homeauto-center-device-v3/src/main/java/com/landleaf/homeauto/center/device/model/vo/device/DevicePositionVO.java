package com.landleaf.homeauto.center.device.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/31
 */
@Data
@ApiModel("带有位置的设备视图对象")
public class DevicePositionVO {

    @ApiModelProperty("房间位置")
    private String room;

    @ApiModelProperty("设备列表")
    private List<DeviceVO> devices;

}
