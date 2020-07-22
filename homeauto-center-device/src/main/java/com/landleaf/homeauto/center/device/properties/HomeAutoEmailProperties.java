package com.landleaf.homeauto.center.device.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yujiumin
 * @version 2020/07/22
 */
@Component
@ConfigurationProperties("homeauto.email")
public class HomeAutoEmailProperties {
}
