package com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 自定义字段选填信息
 *
 * @author wenyilu
 */
@Data
public class SobotExtendFieldDataDTO {

    @ApiModelProperty(value = "自定义字段选项名称", name = "data_name", required = true, notes = "")
    private String data_name;

    @ApiModelProperty(value = "自定义字段选项CODE值", name = "data_value", required = true, notes = "")
    private String data_value;

}
