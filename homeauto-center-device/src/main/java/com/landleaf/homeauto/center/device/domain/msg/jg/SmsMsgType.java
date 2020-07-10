package com.landleaf.homeauto.center.device.domain.msg.jg;

import com.landleaf.homeauto.common.enums.jg.SecondTimeUnitEnum;
import lombok.*;

/**
 * @author Lokiy
 * @date 2019/8/16 12:20
 * @description: 根据枚举存入的短信模板信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SmsMsgType {

    /**
     * 信息类型
     */
    private Integer msgType;

    /**
     * 模板号
     */
    private Integer tempId;

    /**
     * 过期时间  单位为s
     */
    private Integer ttl;


    /**
     * 信息内容
     */
    private String msgContent;

    /**
     * 时间单位模板
     */
    private SecondTimeUnitEnum secondTimeUnitType;
}
