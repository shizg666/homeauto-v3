package com.landleaf.homeauto.center.device.annotation;


import java.lang.annotation.*;

/**
 *  校验请求必传参数或其它自定义逻辑
 * @author pilo
 * @date 2021/1/18 10:52
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestCheckAnnotation {

    /**
     * userId是否必传
     */
    boolean requireUserId() default false;
    /**
     * 家庭是否必须启用
     */
    boolean familyEnable() default false;

}
