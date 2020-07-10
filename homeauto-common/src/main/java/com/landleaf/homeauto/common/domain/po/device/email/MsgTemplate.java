package com.landleaf.homeauto.common.domain.po.device.email;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息模板表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "MsgTemplate对象", description = "消息模板表")
public class MsgTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息模板id")
    private Integer tempId;

    @ApiModelProperty("消息主题")
    private String msgSubject;

    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    @ApiModelProperty(value = "过期时间")
    private Integer ttl;

    @ApiModelProperty(value = "秒数时间转换单位")
    private Integer secondUnitType;

    @ApiModelProperty("具体对应消息大类的 消息类别区别")
    private Integer msgType;

    @ApiModelProperty(value = "信息模板类型 0-系统消息 1-手机消息 2-邮件消息 3-推送消息")
    private Integer tempType;


}
