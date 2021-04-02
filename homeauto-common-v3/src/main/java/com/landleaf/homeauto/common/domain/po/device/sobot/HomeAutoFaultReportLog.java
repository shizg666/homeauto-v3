package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 故障报修状态更新记录
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "HomeautoFaultReportLog对象", description = "")
public class HomeAutoFaultReportLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单号")
    private String ticketId;

    @ApiModelProperty(value = "故障状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;


}
