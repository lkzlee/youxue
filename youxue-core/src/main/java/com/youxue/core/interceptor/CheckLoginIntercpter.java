package com.youxue.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.youxue.core.util.ControllerUtil;

/**
 * 登录拦截器
 */
public class CheckLoginIntercpter extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String userName = ControllerUtil.getCurrentLoginUserName(request);
		if (StringUtils.isNotBlank(userName))
		{
			return super.preHandle(request, response, handler);
		}
		return false;
	}
}
