package com.landleaf.homeauto.common.domain.dto.device.fault;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "HomeAutoFaultDeviceValue对象", description = "")
public class HomeAutoFaultDeviceValueDTO extends HomeAutoFaultDeviceBaseDTO{

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "参考值")
    private String reference;

    @ApiModelProperty(value = "当前值")
    private String current;
    @ApiModelProperty(value = "当前n属性")
    private String attrCode;


}
