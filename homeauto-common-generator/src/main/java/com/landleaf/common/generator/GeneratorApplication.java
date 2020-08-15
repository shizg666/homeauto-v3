package com.landleaf.common.generator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yujiumin
 * @version 2020/8/15
 */
@Configuration
@ComponentScan("com.landleaf.common.generator")
public class GeneratorApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GeneratorApplication.class);
        GeneratorApplication application = applicationContext.getBean(GeneratorApplication.class);
        application.exe();
    }

    private void exe() {

    }

}
