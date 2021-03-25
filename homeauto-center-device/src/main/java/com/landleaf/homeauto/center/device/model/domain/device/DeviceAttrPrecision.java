package com.landleaf.homeauto.center.device.model.domain.device;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 协议属性精度配置
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("device_attr_precision")
@ApiModel(value="DeviceAttrPrecision", description="协议属性精度配置")
public class DeviceAttrPrecision extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "计算系数")
    private String calculationFactor;

    @ApiModelProperty(value = "精度")
    private Integer precision;

    @ApiModelProperty(value = "步长")
    private String step;

    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "最小值")
    private String min;

    @ApiModelProperty(value = "属性主键id")
    private String attrId;

    @ApiModelProperty(value = "设备主键id")
    private String deviceId;


}
