package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 智齿客服平台-工单分类-自定义字段-可选项
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SobotTicketTypeFiledOption对象", description = "智齿客服平台-工单分类-自定义字段-可选项")
public class SobotTicketTypeFiledOption extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "值")
    private String dataValue;

    @ApiModelProperty(value = "名称")
    private String dataName;

    @ApiModelProperty(value = "字段id")
    private String fieldid;

}
