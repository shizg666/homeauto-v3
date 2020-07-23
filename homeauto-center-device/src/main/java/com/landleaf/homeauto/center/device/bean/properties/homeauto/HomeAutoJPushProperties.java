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
@ConfigurationProperties("homeauto.jpush")
public class HomeAutoJPushProperties {

    private Boolean iosEnv = false;
}
