package com.landleaf.homeauto.common.domain.dto.device.sobot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SobotBaseResponseDTO
 * @Description: 通用返回
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotBaseResponseDTO {

    @ApiModelProperty(value = "返回编码", name = "ret_code", required = true, dataType = "String",example = "000000")
    private String ret_code;

    @ApiModelProperty(value = "返回信息", name = "ret_msg", required = true, dataType = "String",example = "操作成功")
    private String ret_msg;
}
