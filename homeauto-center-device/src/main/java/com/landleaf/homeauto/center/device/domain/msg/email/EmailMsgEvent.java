package com.landleaf.homeauto.center.device.domain.msg.email;

import com.landleaf.homeauto.center.device.common.msg.config.event.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Lokiy
 * @date 2019/8/29 9:58
 * @description: 邮件信息事件
 */
@Data
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class EmailMsgEvent extends BaseDomainEvent {

    private EmailMsg emailMsg;

    @Override
    protected String identify() {
        return "Email_Msg_";
    }
}
