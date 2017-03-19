package com.youxue.admin.power.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.youxue.admin.constant.AdminConstant;
import com.youxue.admin.power.annotation.PowerCheck;
import com.youxue.admin.power.constant.PowerConstant;
import com.youxue.admin.power.enums.PowerTypeEnum;
import com.youxue.core.vo.SysUser;

public class PowerInterceptor extends HandlerInterceptorAdapter
{
	public final static Log LOG = LogFactory.getLog(PowerInterceptor.class);
	private List<String> allowUrls;

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String requestUrl = request.getRequestURI();
		//		LOG.info("get request:------" + requestUrl);
		for (String allowUrl : allowUrls)
		{
			if (requestUrl.contains(allowUrl))
			{
				return true;
			}
		}
		if (handler instanceof HandlerMethod)
		{
			HandlerMethod handerMethod = (HandlerMethod) handler;
			Method method = handerMethod.getMethod();
			PowerCheck annotation = method.getAnnotation(PowerCheck.class);
			if (annotation == null)
			{
				//没有添加权限标示的方法 ,直接不做权限校验拦截
				//现在系统没有添加注解，所以在这里直接返回
				return true;
			}
			HttpSession session = request.getSession();
			Object sysUser = session.getAttribute(AdminConstant.CURRENT_USER);
			Object menuList = session.getAttribute(AdminConstant.MENU_LIST);
			if (sysUser == null)
			{
				request.getRequestDispatcher("/login.do").forward(request, response);
				return false;
			}
			SysUser user = (SysUser) sysUser;
			String[] roles = user.getRoleId().split(",");
			for (String role : roles)
			{
				if (Integer.valueOf(role) == PowerTypeEnum.ALL.getValue())
				{
					//超级管理员不做权限校验
					return true;
				}
				if (menuList == null)
				{
					//				LOG.info("no login,adminName:" + adminName + ",menuList:" + menuList + ",powerIds:" + powerIds);
					request.setAttribute(PowerConstant.ERROR_MSG, "您没有该操作的权限,请联系相应的人员");
					request.getRequestDispatcher("/noPower.do").forward(request, response);
					return false;
				}

				if (Integer.valueOf(role) == annotation.powerType())
				{
					return true;
				}
				else
				{
					request.setAttribute(PowerConstant.ERROR_MSG, "您没有该操作的权限,请联系相应的人员");
					request.getRequestDispatcher("/noPower.do").forward(request, response);
					return false;
				}
			}
		}
		return false;
	}

	public List<String> getAllowUrls()
	{
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls)
	{
		this.allowUrls = allowUrls;
	}

}
