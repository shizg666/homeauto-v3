package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@NoArgsConstructor
@ApiModel(value="RoomInfoVO", description="RoomInfoVO")
public class RoomInfoVO {

    @ApiModelProperty("房间id")
    private String id;

    @ApiModelProperty("房间名称")
    private String name;

    @ApiModelProperty("设备条件")
    private List<DeviceInfoVO> devices;



}
