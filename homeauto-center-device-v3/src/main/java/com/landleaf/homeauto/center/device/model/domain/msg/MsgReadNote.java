package com.landleaf.homeauto.center.device.model.domain.msg;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息已读记录表
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="MsgReadNote对象", description="消息已读记录表")
public class MsgReadNote extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "消息类型")
    private Integer msgType;


}
