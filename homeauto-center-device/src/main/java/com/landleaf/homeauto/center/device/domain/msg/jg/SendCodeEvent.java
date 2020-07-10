package com.landleaf.homeauto.center.device.domain.msg.jg;

import com.landleaf.homeauto.center.device.common.msg.config.event.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Lokiy
 * @date 2019/8/15 17:55
 * @description:
 */
@Data
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
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
