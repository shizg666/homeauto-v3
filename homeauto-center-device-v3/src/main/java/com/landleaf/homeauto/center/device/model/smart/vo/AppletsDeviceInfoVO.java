package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 小程序设备展示
 * </p>
 *
 * @author pilo
 */
@Data
@Accessors(chain = true)
@ApiModel(value="AppletsDeviceInfoVO", description="小程序设备展示")
public class AppletsDeviceInfoVO {

    @ApiModelProperty("设备ID")
    private String deviceId;
    @ApiModelProperty("设备号")
    private String deviceSn;
    @ApiModelProperty("设备名称")
    private String deviceName;
    @ApiModelProperty("产品编码")
    private String productCode;
    @ApiModelProperty("ui页面code")
    private String uiCode;
    @ApiModelProperty("品类编码")
    private String categoryCode;
    @ApiModelProperty("家庭ID")
    private String familyId;
    @ApiModelProperty("房间ID")
    private String roomId;
    @ApiModelProperty("屬性集合")
    private List<AppletsAttrInfoVO> attrs;

}
