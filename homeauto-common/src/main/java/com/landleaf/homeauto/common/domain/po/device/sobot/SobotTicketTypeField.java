package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SobotTicketTypeField对象", description="")
public class SobotTicketTypeField extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单分类id")
    private String typeid;

    @ApiModelProperty(value = "自定义字段Id")
    private String fieldid;

    @ApiModelProperty(value = "自定义字段名称")
    private String fieldName;

    @ApiModelProperty(value = "自定义字段类型(1单行文本，2多行文本，3日期，4时间，5 数值，6下拉列表，7复选框，8单选框)")
    private String fieldType;

    @ApiModelProperty(value = "自定义字段是否必填(0 否 1 是)")
    private String fillFlag;

    @ApiModelProperty(value = "对应户式化系统故障保修表中字段名称")
    private String attributeCode;


}
