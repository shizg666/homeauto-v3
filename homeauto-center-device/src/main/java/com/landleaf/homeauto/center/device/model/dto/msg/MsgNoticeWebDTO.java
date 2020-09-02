package com.landleaf.homeauto.center.device.model.dto.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/8/13 14:04
 * @description:
 */
@Data
@ToString(callSuper = true)
@ApiModel("公告消息对象")
public class MsgNoticeWebDTO extends MsgWebDTO{

    @ApiModelProperty( value = "正文", required = true)
    private String content;
}
