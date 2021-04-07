package com.landleaf.homeauto.center.device.model.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CheckDeviceParamBO", description="CheckDeviceParamBO")
public class CheckDeviceParamBO {

    private String name;

    private String sn;

    private String categoryCode;

    @ApiModelProperty(value = "房间ID")
    private Long roomId;

    @ApiModelProperty(value = "户型ID")
    private Long houseTemplateId;

    @ApiModelProperty(value = "家庭ID")
    private Long familyId;

}
