package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备维修指南
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="DeviceRepairGuide对象", description="设备维修指南")
public class DeviceRepairGuide extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "故障原因")
    private String reason;

    @ApiModelProperty(value = "解决办法")
    private String solution;

    @ApiModelProperty(value = "故障类型")
    private Integer type;


}
