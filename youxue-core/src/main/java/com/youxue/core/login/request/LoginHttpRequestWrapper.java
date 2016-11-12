package com.youxue.core.login.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.youxue.core.login.session.LoginHttpSessionWraper;

public class LoginHttpRequestWrapper extends HttpServletRequestWrapper
{
	private String sid = "";

	public LoginHttpRequestWrapper(String sid, HttpServletRequest request)
	{
		super(request);
		this.sid = sid;
	}

	@Override
	public HttpSession getSession(boolean create)
	{
		return new LoginHttpSessionWraper(sid, super.getSession(create));
	}

	@Override
	public HttpSession getSession()
	{
		return new LoginHttpSessionWraper(sid, super.getSession());
	}

}
