package com.landleaf.homeauto.center.device.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName ImagePathConfig
 * @Description: TODO
 * @Author shizg
 * @Date 2020/10/19
 * @Version V1.0
 **/
@Configuration
@Data
public class ImagePathConfig {

    @Value("${homeauto.imgPath}")
    private String context;
}
