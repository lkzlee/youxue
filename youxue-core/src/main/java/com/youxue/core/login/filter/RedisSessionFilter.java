package com.youxue.core.login.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.youxue.core.login.request.LoginHttpRequestWrapper;

public class RedisSessionFilter extends HttpServlet implements Filter
{

	private static Log log = LogFactory.getLog(RedisSessionFilter.class);
	private static final long serialVersionUID = -365105405910803550L;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException
	{
		try
		{
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			String sid = request.getSession().getId();
			if (StringUtils.isNotBlank(request.getParameter("jsessionid")))
			{
				String jsessionid = request.getParameter("jsessionid");
				if (!StringUtils.equals(sid, jsessionid))
				{
					HttpSession session = request.getSession(false);
					if (session != null)
						session.invalidate();
					Cookie cookie = new Cookie("JSESSIONID", jsessionid);
					cookie.setDomain(request.getServerName());
					cookie.setPath("/");
					response.addCookie(cookie);
					sid = jsessionid;
				}
			}

			filterChain.doFilter(new LoginHttpRequestWrapper(sid, request), servletResponse);
		}
		catch (Exception e)
		{
			if (e instanceof SQLException)
			{
				log.fatal("数据库操作错误" + e.getMessage(), e);
			}
			else
			{
				log.error("未知异常" + e.getMessage(), e);
			}
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

}
