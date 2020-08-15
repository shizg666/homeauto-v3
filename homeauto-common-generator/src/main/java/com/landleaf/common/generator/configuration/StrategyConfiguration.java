package com.landleaf.common.generator.configuration;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.landleaf.homeauto.model.po.base.BasePO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@Configuration
public class StrategyConfiguration {

    @Bean
    public StrategyConfig strategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setSuperEntityClass(BasePO.class);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setSuperControllerClass("");
        strategyConfig.setSuperEntityColumns("id", "create_time", "create_user", "update_time", "update_user");
        strategyConfig.setInclude("");
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setTablePrefix("");
        return strategyConfig;
    }

}
