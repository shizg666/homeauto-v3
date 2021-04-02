package com.landleaf.homeauto.center.device.bean.properties.homeauto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 智齿客服系统配置参数
 *
 * @author wenyilu
 */
@Data
@Component
@ConfigurationProperties("homeauto.sobot")
public class HomeAutoSobotProperties {

    private HomeAutoSobotCustomerProperties customer;

    private HomeAutoSobotTicketProperties ticket;

}
