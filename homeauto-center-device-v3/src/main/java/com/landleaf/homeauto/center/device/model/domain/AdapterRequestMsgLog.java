package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 控制命令操作日志
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "AdapterRequestMsgLog对象", description = "控制命令操作日志")
public class AdapterRequestMsgLog extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "操作内容")
    private String content;

    @ApiModelProperty(value = "返回结果")
    private Integer code;

    @ApiModelProperty(value = "返回结果")
    private String message;

    @ApiModelProperty(value = "家庭id")
    private Long familyId;

    @ApiModelProperty(value = "家庭编码")
    private String familyCode;

    @ApiModelProperty(value = "消息id")
    private String messageId;

    @ApiModelProperty(value = "终端mac")
    private String terminalMac;

    @ApiModelProperty(value = "终端类型")
    private Integer terminalType;

    @ApiModelProperty(value = "消息类别")
    private String messageName;

    @ApiModelProperty(value = "重试次数")
    private Integer retryTimes;


}
