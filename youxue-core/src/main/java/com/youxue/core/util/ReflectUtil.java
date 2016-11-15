package com.youxue.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 反射util
 * 
 * 
 */
public class ReflectUtil
{
	private static final Log logger = LogFactory.getLog(ReflectUtil.class);

	/**
	 * 设置两个不同类型对象的值
	 * 适用于两个对象成员名相同，并且适用于枚举类型的情况
	 * @param targetObj
	 * @param sourceObj
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static void setObjectFiledsValue(Object targetObj, Object sourceObj) throws IllegalArgumentException,
			IllegalAccessException
	{
		if (targetObj == null || sourceObj == null)
		{
			return;
		}
		Field[] fields = sourceObj.getClass().getDeclaredFields();
		for (Field f : fields)
		{
			f.setAccessible(true);
			if ("serialVersionUID".equalsIgnoreCase(f.getName()))
			{
				continue;
			}
			if (f.getType().isAssignableFrom(Enum.class))
			{
				continue;
			}
			if (f.get(sourceObj) == null)
			{
				continue;
			}
			setFieldValue(targetObj, f.getName(), f.getType(), f.get(sourceObj));
		}
	}

	@SuppressWarnings("unchecked")
	public static void setFieldValue(Object target, String fname, Class ftype, Object fvalue)
	{
		if (target == null || fname == null || "".equals(fname)
				|| (fvalue != null && !ftype.isAssignableFrom(fvalue.getClass())))
		{
			return;
		}
		Class clazz = target.getClass();
		try
		{
			Method method = clazz.getDeclaredMethod(
					"set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), ftype);

			if (!Modifier.isPublic(method.getModifiers()))
			{
				method.setAccessible(true);
			}
			method.invoke(target, fvalue);

		}
		catch (Exception me)
		{
			//			if (logger.isDebugEnabled())
			//			{
			//				logger.debug(me);
			//			}
			try
			{
				Field field = clazz.getDeclaredField(fname);
				if (!Modifier.isPublic(field.getModifiers()))
				{
					field.setAccessible(true);
				}
				field.set(target, fvalue);
			}
			catch (Exception fe)
			{
				if (logger.isDebugEnabled())
				{
					logger.debug(fe);
				}
			}
		}
	}

}
