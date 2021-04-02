package com.landleaf.homeauto.center.device.model.vo.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭设备
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@ApiModel("家庭设备")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceVO {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("产品码")
    private String productCode;

    @ApiModelProperty("品类码")
    private String categoryCode;

    @ApiModelProperty("是否显示总控属性")
    private Integer flag;

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
