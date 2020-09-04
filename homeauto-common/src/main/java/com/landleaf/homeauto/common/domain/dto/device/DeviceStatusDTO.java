package com.landleaf.homeauto.common.domain.dto.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 设备状态数据传输对象
 *
 * @author Yujiumin
 * @version 2020/9/4
 */
@Data
@NoArgsConstructor
@ApiModel("设备状态数据传输对象")
public class DeviceStatusDTO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("品类Code")
    private String category;

    @ApiModelProperty("设备序列号")
    private String deviceSn;

    @ApiModelProperty("属性集合")
    private Map<String, String> attributes;

}
