package com.youxue.core.login.session;

import java.util.HashMap;
import java.util.Map;

import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.PropertyUtils;
import com.youxue.core.util.SpringContextHolder;

public class SessionService
{

	private static volatile SessionService instance = null;

	public static Integer SESSION_TIMEOUT = 2400; //session超时时间,40分钟

	public static SessionService getInstance()
	{
		if (instance == null)
		{
			instance = new SessionService();
		}
		return instance;
	}

	public static JedisProxy getSessionJedisClient()
	{
		return ((JedisProxy) SpringContextHolder.getBean("jedisProxy"));
	}

	public Map getSession(String id)
	{
		@SuppressWarnings("rawtypes")
		Map session = new HashMap();
		return session;
	}

	public void saveSession(String id, Map session)
	{
		JedisProxy jedis = getSessionJedisClient();
		jedis.hessianSetex(id, session, PropertyUtils.getPropertyIntValue("redis.session.timeout", SESSION_TIMEOUT));
	}

	public void removeSession(String id)
	{
		JedisProxy jedis = getSessionJedisClient();
		jedis.del(id);
	}
}
