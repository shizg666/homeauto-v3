package com.landleaf.homeauto.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭常用设备
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("家庭常用设备")
public class FamilyCommonDeviceVO {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备位置")
    private String position;

    @ApiModelProperty("设备图标")
    private String deviceIcon;

    @ApiModelProperty("设备开关状态")
    private Integer deviceSwitch;

    @ApiModelProperty("索引值")
    private Integer index;

}
