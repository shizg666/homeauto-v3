package com.landleaf.homeauto.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Verify {
	/**
	 * 判断是否可为空,true可以为空
	 * 
	 * @return 是否可以为空
	 */
	boolean nullable() default false;
	
	/**
	 * 判断是否为数字
	 * 
	 * @return 是否是数字
	 */
	boolean isNum() default false;
}
