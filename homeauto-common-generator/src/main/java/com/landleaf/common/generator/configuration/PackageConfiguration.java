package com.landleaf.common.generator.configuration;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@Configuration
public class PackageConfiguration {

    @Bean
    public PackageConfig packageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName("");
        packageConfig.setParent("com.landleaf.homeauto.center.device");
        return packageConfig;
    }

}
