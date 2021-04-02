package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName DeviceAttrPrecisionValueDTO
 * @Description: 返回数值类型状态值
 * @Author wyl
 * @Date 2021/1/29
 * @Version V1.0
 **/
@Builder
@Data
public class DeviceAttrPrecisionValueDTO {

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "计算系数")
    private String calculationFactor;

    @ApiModelProperty(value = "精度")
    private Integer precision;

    @ApiModelProperty(value = "步长")
    private String step;

    @ApiModelProperty(value = "最大值")
    private Object maxValue;

    @ApiModelProperty(value = "最小值")
    private Object minValue;

    @ApiModelProperty(value = "当前值")
    private Object currentValue;

    public DeviceAttrPrecisionValueDTO(String unit, String calculationFactor, Integer precision, String step, Object maxValue, Object minValue, Object currentValue) {
        this.unit = unit;
        this.calculationFactor = calculationFactor;
        this.precision = precision;
        this.step = step;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.currentValue = currentValue;
    }
}
