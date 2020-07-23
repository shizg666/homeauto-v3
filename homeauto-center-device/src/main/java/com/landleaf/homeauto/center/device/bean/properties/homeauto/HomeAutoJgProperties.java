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
@ConfigurationProperties("homeauto.jg")
public class HomeAutoJgProperties {

    private String appKey = "1cee39b5c6f19e4712ee0351";

    private String masterSecret = "d69904ee9107bb93e4ce84f8";

}
