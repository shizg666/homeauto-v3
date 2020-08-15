package com.landleaf.common.generator.configuration;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSourceConfig dataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:postgresql://52.130.74.157:5431/homeauto?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dataSourceConfig.setSchemaName("public");
        dataSourceConfig.setDriverName("org.postgresql.Driver");
        dataSourceConfig.setUsername("homeauto");
        dataSourceConfig.setPassword("landleaf@123.com");
        return dataSourceConfig;
    }

}
