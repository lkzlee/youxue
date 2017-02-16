package com.youxue.admin.login.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.youxue.admin.constant.AdminConstant;

/**
 * @author Masterwind
 * 2015年12月28日下午5:42:49

 * @Description 登录拦截器，除了登录链接，其他请求必须处于登录状态
 */
public class LoginInterceptor implements HandlerInterceptor
{
	public final static Log LOG = LogFactory.getLog(LoginInterceptor.class);

	private List<String> allowUrls;

	public List<String> getAllowUrls()
	{
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls)
	{
		this.allowUrls = allowUrls;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String requestUrl = request.getRequestURI();
		for (String allowUrl : allowUrls)
		{
			if (requestUrl.contains(allowUrl))
			{
				return true;
			}
		}

		HttpSession session = request.getSession();
		if (session != null && session.getAttribute(AdminConstant.CURRENT_USER_NAME) != null)
		{
			LOG.info("----in session ------,userName:" + session.getAttribute(AdminConstant.CURRENT_USER_NAME)
					+ "| requestUrl:" + request.getRequestURI());
			return true;
		}
		LOG.info("----out of session,request failed ------,userName:"
				+ session.getAttribute(AdminConstant.CURRENT_USER_NAME) + "| requestUrl:" + request.getRequestURI());
		request.setAttribute("errorMessage", "非登录状态，请重新登录!");
		request.getRequestDispatcher("/login.do").forward(request, response);
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		// TODO Auto-generated method stub

	}

}
