package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.model.domain.EmailMsg;
import com.landleaf.homeauto.center.device.eventbus.event.base.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Lokiy
 * @date 2019/8/29 9:58
 * @description: 邮件信息事件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class EmailMsgEvent extends BaseDomainEvent {

    private EmailMsg emailMsg;

    @Override
    protected String identify() {
        return "Email_Msg_";
    }
}
