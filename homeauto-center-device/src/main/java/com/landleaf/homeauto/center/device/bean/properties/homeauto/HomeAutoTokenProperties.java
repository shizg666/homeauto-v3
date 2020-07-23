package com.landleaf.homeauto.center.device.bean.properties.homeauto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Yujiumin
 * @version 2020/07/22
 */
@Data
@Component
@ConfigurationProperties("homeauto.token")
public class HomeAutoTokenProperties {

    private Boolean enable = true;

}
