package com.landleaf.homeauto.common.domain.po.device.jg;


import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 极光短信验证码记录
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "JsmsSendHistory对象", description = "极光短信验证码记录")
public class JsmsSendHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "极光返回的消息id")
    private String messageId;

    @ApiModelProperty(value = "极光模板id")
    @TableField("tempId")
    private Integer tempId;

    @ApiModelProperty(value = "发送手机号")
    private String mobile;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "失效时间")
    private Integer ttl;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;


}
