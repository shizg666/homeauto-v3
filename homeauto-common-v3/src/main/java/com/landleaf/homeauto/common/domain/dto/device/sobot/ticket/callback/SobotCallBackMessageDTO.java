package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.callback;

import lombok.Data;

import java.util.List;

/**
 * @ClassName CallBackMessageDTO
 * @Description: 智齿客服平台推送消息体
 * @Author wyl
 * @Date 2020/8/28
 * @Version V1.0
 **/
@Data
public class SobotCallBackMessageDTO {

    /**
     * 产品编码
     */
    private String sys_code;
    /**
     * 消息类型
     * ticket
     */
    private String type;
    /**
     * 消息内容
     */
    private List<SobotCallBackContentDTO> content;


}
