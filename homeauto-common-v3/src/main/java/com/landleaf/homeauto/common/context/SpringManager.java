package com.landleaf.homeauto.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 注入Spring容器
 */
@Component
public class SpringManager implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringManager.applicationContext = applicationContext;
    }

    public synchronized static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

}
