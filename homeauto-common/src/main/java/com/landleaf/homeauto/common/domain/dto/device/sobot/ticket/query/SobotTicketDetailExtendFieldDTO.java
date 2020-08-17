package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query;

import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldDataDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 自定义字段
 *
 * @author wenyilu
 */
@Data
public class SobotTicketDetailExtendFieldDTO {

    @ApiModelProperty(value = "自定义字段ID", name = "fieldid", required = true, notes = "")
    private String fieldid;

    @ApiModelProperty(value = "自定义字段名称", name = "field_name", required = true, notes = "")
    private String field_name;

    @ApiModelProperty(value = "自定义字段类型", name = "field_type", required = true, notes = "1单行文本，2多行文本，3日期，4时间，5 数值，6下拉列表，7复选框，8单选框")
    private String field_type;

    @ApiModelProperty(value = "自定义字段是否必填", name = "field_name", required = true, notes = "0 否 1 是")
    private String fill_flag;

    @ApiModelProperty(value = "选择型字段选项文本值\t", name = "field_text", required = true, notes = "")
    private String field_text;

    @ApiModelProperty(value = "自定义字段值", name = "field_value", required = true, notes = "")
    private String field_value;


}
