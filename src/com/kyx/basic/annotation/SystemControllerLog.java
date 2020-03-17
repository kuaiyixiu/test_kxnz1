package com.kyx.basic.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 拦截Controller
 * 
 * @author tyg
 * 
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
	String module() default ""; //模块名称
	String description() default ""; //业务方法名称
//	String author() default ""; //作者
//	String logconstants() default ""; //log4j的模块
}