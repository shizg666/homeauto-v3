package com.landleaf.homeauto.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component // 注入spring容器
public class SpringManager implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringManager.applicationContext = applicationContext;
    }

    public synchronized static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

}
