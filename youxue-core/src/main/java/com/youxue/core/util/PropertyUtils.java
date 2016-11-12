package com.youxue.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * properties帮助类
 * 默认加载config.properties
 */
public class PropertyUtils
{

	private static Logger logger = Logger.getLogger(PropertyUtils.class);

	private static final String config = "config.properties";

	private static Map<String, String> config_map = new HashMap<String, String>();

	static
	{
		load(config);
	}

	public static String getProperty(String key)
	{
		if (StringUtils.isBlank(key))
		{
			return null;
		}
		return config_map.get(key);
	}

	public static String getProperty(String key, String defaultValue)
	{
		if (StringUtils.isEmpty(key))
		{
			return (StringUtils.isEmpty(defaultValue) ? null : defaultValue);
		}
		return (StringUtils.isEmpty(config_map.get(key)) ? defaultValue : config_map.get(key));
	}

	public static int getPropertyIntValue(String key, int defaultValue)
	{
		if (StringUtils.isEmpty(key))
		{
			return defaultValue;
		}
		return (StringUtils.isEmpty(config_map.get(key)) || !isInt(config_map.get(key))) ? defaultValue : Integer
				.parseInt(config_map.get(key));
	}

	private static boolean isInt(String n)
	{
		try
		{
			Integer.parseInt(n);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	private static void load(String name)
	{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		Properties p = new Properties();
		try
		{
			p.load(is);
			if (config.equals(name))
			{
				for (Map.Entry e : p.entrySet())
				{
					config_map.put((String) e.getKey(), (String) e.getValue());
				}
			}

		}
		catch (IOException e)
		{
			logger.fatal("load property file failed. file name: " + name, e);
		}
	}

	public static void main(String[] args)
	{
		System.out.println(getProperty("shop.netease.prikey"));
	}
}
