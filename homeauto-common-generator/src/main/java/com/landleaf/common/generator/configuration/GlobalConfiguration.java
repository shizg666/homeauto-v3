package com.landleaf.common.generator.configuration;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@Configuration
public class GlobalConfiguration {

    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir("/src/main/java");
        globalConfig.setAuthor(System.getProperty("user.name"));
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(true);
        return globalConfig;
    }
}
