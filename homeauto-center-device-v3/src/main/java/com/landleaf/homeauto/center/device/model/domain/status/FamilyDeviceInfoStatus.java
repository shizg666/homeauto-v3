package com.landleaf.homeauto.center.device.model.domain.status;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备基本状态表(暖通、数值、在线离线标记)
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyDeviceInfoStatus对象", description="设备基本状态表(暖通、数值、在线离线标记)")
public class FamilyDeviceInfoStatus extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭ID")
    private Long familyId;

    @ApiModelProperty(value = "设备编码")
    private String deviceSn;

    @ApiModelProperty(value = "设备ID")
    private Long deviceId;

    @ApiModelProperty(value = "暖通故障标记")
    private Integer havcFaultFlag;

    @ApiModelProperty(value = "数值故障标记")
    private Integer valueFaultFlag;

    @ApiModelProperty(value = "在线离线标记")
    private Integer onlineFlag;

    private String categoryCode;

    private String productCode;




}
