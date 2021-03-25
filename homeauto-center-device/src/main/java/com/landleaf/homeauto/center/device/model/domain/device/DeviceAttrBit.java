package com.landleaf.homeauto.center.device.model.domain.device;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 协议属性二进制值配置
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("device_attr_bit")
@ApiModel(value="DeviceAttrBit", description="协议属性二进制值配置")
public class DeviceAttrBit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;


    @TableField("bit_1")
    @ApiModelProperty(value = "1 代表的描述")
    private String bit1;

    @TableField("bit_0")
    @ApiModelProperty(value = "0 代表的描述")
    private String bit0;

    @ApiModelProperty(value = "位")
    private Integer bitPos;

    @ApiModelProperty(value = "属性主键id")
    private String attrId;

    @ApiModelProperty(value = "设备主键id")
    private String deviceId;




}
