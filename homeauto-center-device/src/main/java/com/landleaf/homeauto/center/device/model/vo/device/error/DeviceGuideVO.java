package com.landleaf.homeauto.center.device.model.vo.device.error;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.enums.category.ErrorStatusEnum;
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
@ApiModel(value="DeviceGuideVO", description="DeviceGuideVO")
public class DeviceGuideVO  {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "故障原因")
    private String reason;

    @ApiModelProperty(value = "解决办法")
    private String solution;

    /**
     * {@link com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum}
     */
    @ApiModelProperty(value = "故障类型")
    private Integer type;

    @ApiModelProperty(value = "故障类型str")
    private String typeStr;

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = AttributeErrorTypeEnum.getInstByType(type) !=null?AttributeErrorTypeEnum.getInstByType(type).getName():"";
    }
}
