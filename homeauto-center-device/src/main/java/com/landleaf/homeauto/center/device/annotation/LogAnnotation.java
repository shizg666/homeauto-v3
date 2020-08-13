package com.landleaf.homeauto.center.device.annotation;

import com.landleaf.homeauto.common.enums.log.OperationLogTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    /**
     * 名称
     */
    String name();

    /**
     * 日志类型
     */
    OperationLogTypeEnum type() default OperationLogTypeEnum.PROJECT_OPERATION;
}
