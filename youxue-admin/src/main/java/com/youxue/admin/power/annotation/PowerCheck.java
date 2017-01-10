package com.youxue.admin.power.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Masterwind
 * 2016年1月8日下午2:21:06

 * @Description 权限检查注解
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(
{ ElementType.METHOD, ElementType.TYPE })
public @interface PowerCheck
{
	int powerType() default 0;//权限标识
}
