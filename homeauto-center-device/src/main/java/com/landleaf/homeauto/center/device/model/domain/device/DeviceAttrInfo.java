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
 * 协议属性信息
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("device_attr_info")
@ApiModel(value="DeviceAttrInfo", description="协议属性信息")
public class DeviceAttrInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "通信编码")
    private String code;

    @ApiModelProperty(value = "操作权限")
    private String operateAcl;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "值类型")
    private Integer valueType;

    @ApiModelProperty(value = "app是否读写")
    private Integer appFlag;

    @ApiModelProperty(value = "设备主键id")
    private String deviceId;

    @ApiModelProperty(value = "品类")
    private String category;

    @ApiModelProperty(value = "户型id")
    private String houseTemplateId;

    @ApiModelProperty(value = "设备code")
    private String deviceCode;




}
