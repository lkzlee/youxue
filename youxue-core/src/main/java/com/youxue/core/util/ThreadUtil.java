package com.youxue.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author MasterWind
 * 2015年9月16日 下午2:48:16
 * @Description 线程相关方法
 */
public class ThreadUtil
{
	private final static Log LOG = LogFactory.getLog(ThreadUtil.class);

	public static void sleep(int mills)
	{
		try
		{
			Thread.sleep(mills);
		}
		catch (Exception e)
		{
			LOG.error("休眠失败 sleep error:", e);
		}
	}
}
