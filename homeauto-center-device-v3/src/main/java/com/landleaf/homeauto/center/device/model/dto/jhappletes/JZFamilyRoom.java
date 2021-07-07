package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName JZFamilyRoom
 * @Description: TODO
 * @Author shizg
 * @Date 2021/7/7
 * @Version V1.0
 **/
@Data
@ApiModel(value="JZFamilyRoom", description="房间信息")
public class JZFamilyRoom {

    @ApiModelProperty(value = "房间id")
    private Long roomId;
    @ApiModelProperty(value = "房间名称")
    private String name;
    @ApiModelProperty(value = "房间类型")
    private Integer type;
    @ApiModelProperty(value = "设备列表")
    private List<DeviceInfo> devices;
}
