package com.landleaf.homeauto.center.device.bean.properties.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yujiumin
 * @version 2020/07/23
 */
@Data
@Component
@ConfigurationProperties("mqtt.server")
public class MqttServerProperties {

    private String url = "tcp://40.73.77.122:1883";

    private String clientId = "homeauto-center-device-local";

}
