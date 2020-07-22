package com.landleaf.homeauto.center.device.eventbus.event;

import com.landleaf.homeauto.center.device.model.domain.ShSmsMsgDomain;
import com.landleaf.homeauto.center.device.eventbus.event.base.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Lokiy
 * @date 2019/8/15 17:55
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class SendCodeEvent extends BaseDomainEvent {

    private ShSmsMsgDomain shSmsMsg;

    private String messageId;

    private boolean dbFlag;

    private boolean redisFlag;

    public SendCodeEvent(ShSmsMsgDomain shSmsMsg, String messageId){
        this.shSmsMsg = shSmsMsg;
        this.messageId = messageId;
        this.dbFlag = true;
        this.redisFlag = true;
    }

    @Override
    protected String identify() {
        return "Msg_Type_";
    }
}
