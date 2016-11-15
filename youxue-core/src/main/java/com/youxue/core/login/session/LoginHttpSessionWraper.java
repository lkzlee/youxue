package com.youxue.core.login.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import com.youxue.core.redis.JedisProxy;

public class LoginHttpSessionWraper extends HttpSessionWrapper
{
	private String sid = "";

	public LoginHttpSessionWraper(String sid, HttpSession session)
	{
		super(session);
		this.sid = sid;
	}

	private JedisProxy getJedisProxyClient()
	{
		return SessionService.getSessionJedisClient();
	}

	@Override
	public Object getAttribute(String arg0)
	{
		Map map = (Map) getJedisProxyClient().hessianGet(sid);
		if (map == null)
			return null;
		return map.get(arg0);
	}

	@Override
	public Enumeration getAttributeNames()
	{
		Map map = (Map) getJedisProxyClient().hessianGet(sid);
		if (map == null)
		{
			return new Enumerator(new TreeSet(), true);
		}
		return (new Enumerator(map.keySet(), true));
	}

	@Override
	public void invalidate()
	{
		Map map = (Map) getJedisProxyClient().hessianGet(sid);
		if (map != null)
		{
			map.clear();
			SessionService.getInstance().removeSession(this.sid);
		}
	}

	@Override
	public void removeAttribute(String arg0)
	{
		Map map = (Map) getJedisProxyClient().hessianGet(sid);
		if (map != null)
		{
			map.remove(arg0);
			SessionService.getInstance().saveSession(this.sid, map);
		}
	}

	@Override
	public void removeValue(String arg0)
	{
		removeAttribute(arg0);
	}

	@Override
	public void setAttribute(String arg0, Object arg1)
	{

		Map map = (Map) getJedisProxyClient().hessianGet(sid);
		if (map == null)
		{
			map = new HashMap();
		}
		map.put(arg0, arg1);
		SessionService.getInstance().saveSession(this.sid, map);
	}

	@Override
	public Object getValue(String arg0)
	{
		return getAttribute(arg0);
	}

	@Override
	public void putValue(String arg0, Object arg1)
	{
		setAttribute(arg0, arg1);
	}

}
