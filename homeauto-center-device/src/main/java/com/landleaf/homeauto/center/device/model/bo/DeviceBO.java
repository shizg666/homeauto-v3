package com.landleaf.homeauto.center.device.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 设备业务对象(有用的,别删)
 *
 * @author Yujiumin
 * @version 2020/9/18
 */
@Data
@NoArgsConstructor
@ApiModel("设备业务对象")
public class DeviceBO {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("设备序列号")
    private String deviceSn;

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("家庭编码")
    private String familyCode;

    @ApiModelProperty("楼层ID")
    private String floorId;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("房间ID")
    private String roomId;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("产品ID")
    private String productId;

    @ApiModelProperty("产品码")
    private String productCode;

    @ApiModelProperty("产品图标")
    private String productIcon;

    @ApiModelProperty("品类ID")
    private String categoryId;

    @ApiModelProperty("品类码")
    private String categoryCode;

    @ApiModelProperty("终端ID")
    private String terminalId;

    @ApiModelProperty("终端MAC地址")
    private String terminalMac;

    @ApiModelProperty("设备属性集合")
    private List<String> deviceAttributeList;

}
