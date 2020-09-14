package com.landleaf.homeauto.common.domain.dto.sync;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/8/29 10:06
 * @description: 邮箱信息
 */
@Data
@ToString
@ApiModel(value="SyncSceneDeviceBO", description="SyncSceneDeviceBO")
public class SyncSceneDeviceBO {

    @ApiModelProperty("设备号")
    private String sn;

    @ApiModelProperty("产品code")
    private String productCode;


}
