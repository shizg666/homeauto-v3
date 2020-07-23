package com.landleaf.homeauto.center.device.bean.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yujiumin
 * @version 2020/07/22
 */
@Data
@Component
@ConfigurationProperties("homeauto.rocketmq.producer")
public class HomeAutoRocketMqProducerProperties {

    private Boolean enable = true;

    private String groupName = "homeauto-test-p";

    private String nameSrvAddr = "49.232.174.101:9876";

    private Integer maxMessageSize = 4096;

    private Integer sendMsgTimeout = 30_000;

}
