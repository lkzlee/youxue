package com.youxue.core.redis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author KevinLiao
 * 在方法或者类上添加该注释后，自动添加JedisAop的释放连接功能
 * 
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(
{ ElementType.METHOD, ElementType.TYPE })
public @interface JedisWay
{

}
