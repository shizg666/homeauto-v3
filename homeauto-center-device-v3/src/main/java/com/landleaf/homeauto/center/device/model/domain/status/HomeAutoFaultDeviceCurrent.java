package com.landleaf.homeauto.center.device.model.domain.status;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备当前故障值
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoFaultDeviceCurrent对象", description="设备当前故障值")
public class HomeAutoFaultDeviceCurrent extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭")
    private Long familyId;
    @ApiModelProperty(value = "设备")
    private Long deviceId;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "所属楼盘")
    private Long realestateId;

    @ApiModelProperty(value = "所属项目")
    private Long projectId;

    @ApiModelProperty(value = "故障编码")
    private String code;
    @ApiModelProperty(value = "故障值")
    private String value;
    @ApiModelProperty(value = "故障类型(1:二进制故障，2：数值异常，3：通信)")
    private Integer type;


}
