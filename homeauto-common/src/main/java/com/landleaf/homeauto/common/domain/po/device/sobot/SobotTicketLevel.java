package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 智齿客服平台-优先级
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SobotTicketLevel对象", description = "智齿客服平台-优先级")
public class SobotTicketLevel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "优先级值")
    private String dictValue;

    @ApiModelProperty(value = "优先级名称")
    private String dictName;


}
