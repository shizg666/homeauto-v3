package com.landleaf.homeauto.center.device.model.domain.device;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 协议属性具体值配置
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("device_attr_select")
@ApiModel(value="DeviceAttrSelect", description="协议属性具体值配置")
public class DeviceAttrSelect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性值名称")
    private String name;

    @ApiModelProperty(value = "属性值")
    private String value;


    @ApiModelProperty(value = "属性主键id")
    private String attrId;

    @ApiModelProperty(value = "设备主键id")
    private String deviceId;


}
