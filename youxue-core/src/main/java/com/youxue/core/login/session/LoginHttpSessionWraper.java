package com.youxue.core.login.session;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.youxue.core.redis.JedisProxy;

public class LoginHttpSessionWraper extends HttpSessionWrapper
{
	private String sid = "";
	private Map map = null;

	public LoginHttpSessionWraper(String sid, HttpSession session)
	{
		super(session);
		this.sid = sid;
		this.map = SessionService.getInstance().getSession(sid);
	}

	private JedisProxy getJedisProxyClient()
	{
		return SessionService.getSessionJedisClient();
	}

	@Override
	public Object getAttribute(String arg0)
	{

		return super.getAttribute(arg0);
	}

	@Override
	public Enumeration getAttributeNames()
	{
		// TODO Auto-generated method stub
		return super.getAttributeNames();
	}

	@Override
	public long getCreationTime()
	{
		// TODO Auto-generated method stub
		return super.getCreationTime();
	}

	@Override
	public String getId()
	{
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public long getLastAccessedTime()
	{
		// TODO Auto-generated method stub
		return super.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval()
	{
		// TODO Auto-generated method stub
		return super.getMaxInactiveInterval();
	}

	@Override
	public ServletContext getServletContext()
	{
		// TODO Auto-generated method stub
		return super.getServletContext();
	}

	@Override
	public HttpSessionContext getSessionContext()
	{
		// TODO Auto-generated method stub
		return super.getSessionContext();
	}

	@Override
	public Object getValue(String arg0)
	{
		// TODO Auto-generated method stub
		return super.getValue(arg0);
	}

	@Override
	public String[] getValueNames()
	{
		// TODO Auto-generated method stub
		return super.getValueNames();
	}

	@Override
	public void invalidate()
	{
		// TODO Auto-generated method stub
		super.invalidate();
	}

	@Override
	public boolean isNew()
	{
		// TODO Auto-generated method stub
		return super.isNew();
	}

	@Override
	public void putValue(String arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		super.putValue(arg0, arg1);
	}

	@Override
	public void removeAttribute(String arg0)
	{
		// TODO Auto-generated method stub
		super.removeAttribute(arg0);
	}

	@Override
	public void removeValue(String arg0)
	{
		// TODO Auto-generated method stub
		super.removeValue(arg0);
	}

	@Override
	public void setAttribute(String arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		super.setAttribute(arg0, arg1);
	}

	@Override
	public void setMaxInactiveInterval(int arg0)
	{
		// TODO Auto-generated method stub
		super.setMaxInactiveInterval(arg0);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable
	{
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return super.toString();
	}
}
