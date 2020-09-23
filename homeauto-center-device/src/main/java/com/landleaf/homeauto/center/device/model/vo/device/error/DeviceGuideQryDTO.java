package com.landleaf.homeauto.center.device.model.vo.device.error;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.qry.BaseQry;
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
@Accessors(chain = true)
@ApiModel(value="DeviceGuideQryDTO", description="设备维修指南查询")
public class DeviceGuideQryDTO  extends BaseQry {


    @ApiModelProperty(value = "故障原因")
    private String reason;

    /**
     * {@link com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum}
     */
    @ApiModelProperty(value = "故障类型")
    private Integer type;


}
