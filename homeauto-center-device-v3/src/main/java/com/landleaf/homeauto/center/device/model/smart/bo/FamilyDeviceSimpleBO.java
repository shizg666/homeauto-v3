package com.landleaf.homeauto.center.device.model.smart.bo;

import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 家庭设备业务对象
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭设备业务对象")
public class FamilyDeviceSimpleBO {

    @ApiModelProperty("设备ID")
    private Long deviceId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("品类编码")
    private String categoryCode;

    @ApiModelProperty("是否系统设备1：是")
    private Integer systemFlag;

    @ApiModelProperty("产品图标")
    private String productIcon;

    @ApiModelProperty("产品图片")
    private String productImage;
}
