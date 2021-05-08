package com.landleaf.homeauto.common.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * mqtt 配置
 * @author wenyilu
 */

@Data
@Component
@ConfigurationProperties("homeauto.mqtt")
public class MqttConfigProperty {

    private Boolean enable;
    /**
     * mqtt的url
     */
    private String serverUrl;

    /**
     * mqtt的clientId
     */
    private String serverClientId;


    /**
     * 用户名
     */
    private String serverUserName;

    /**
     * 密码
     */
    private String serverPassword;

    /**
     * CA证书
     */
    private String caCrt;

    /**
     * mqtt客户端crt
     */
    private String clientCrt;
    /**
     * mqtt客户端key
     */
    private String clientKey;

    private Boolean httpEnable;

    private String httpUrl;

    private String httpUser;

    private String httpPassword;
}
