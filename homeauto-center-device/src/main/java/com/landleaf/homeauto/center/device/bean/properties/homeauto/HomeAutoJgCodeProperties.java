package com.landleaf.homeauto.center.device.bean.properties.homeauto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yujiumin
 * @version 2020/07/22
 */
@Data
@Component
@ConfigurationProperties("homeauto.jg.code")
public class HomeAutoJgCodeProperties {

    private String redisKeyPrefix = "sms_code_";

    private String mobileHasSend = "mobile_has_send:";

    private Integer mobileHasSendTtl = 60;

    private Integer ipDailyTimesLimit = 5;

}
