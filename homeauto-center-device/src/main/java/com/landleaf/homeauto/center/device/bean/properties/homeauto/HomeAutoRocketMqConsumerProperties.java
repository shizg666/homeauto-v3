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
@ConfigurationProperties("homeauto.rocketmq.consumer")
public class HomeAutoRocketMqConsumerProperties {

    private Boolean enable = true;

    private String groupName = "homeauto-test-p";

    private String nameSrvAddr = "49.232.174.101:9876";

    private String[] topics = {};

    private Integer consumeThreadMin = 16;

    private Integer consumeThreadMax = 32;

    private Integer consumeMessageBatchMaxSize = 10;

}
