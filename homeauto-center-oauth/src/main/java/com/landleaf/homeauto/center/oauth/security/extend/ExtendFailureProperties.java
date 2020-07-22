package com.landleaf.homeauto.center.oauth.security.extend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wenyilu
 */
@ConfigurationProperties(prefix = "homeauto.security.failure")
@Data
public class ExtendFailureProperties {

    private String statusParam = "code";
    private String msgParam = "xml";

}
