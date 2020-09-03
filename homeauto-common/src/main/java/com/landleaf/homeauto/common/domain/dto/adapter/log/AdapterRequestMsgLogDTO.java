package com.landleaf.homeauto.common.domain.dto.adapter.log;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenyilu
 */
@Data
public class AdapterRequestMsgLogDTO {

    @ApiModelProperty(value = "操作内容")
    private String content;

    @ApiModelProperty(value = "返回结果")
    private Integer code;

    @ApiModelProperty(value = "返回结果")
    private String message;

    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @ApiModelProperty(value = "家庭编码")
    private String familyCode;

    @ApiModelProperty(value = "消息id")
    @TableField("messageId")
    private String messageId;

    @ApiModelProperty(value = "终端mac")
    private String terminalMac;

    @ApiModelProperty(value = "终端类型")
    private Integer terminalType;

    @ApiModelProperty(value = "消息类别")
    private String messageName;
}
