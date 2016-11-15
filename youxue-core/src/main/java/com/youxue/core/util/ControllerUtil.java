package com.youxue.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ControllerUtil
{
	public static final String SESSION_LOGIN_USER_KEY = "currentLoginUser";
	public static final String SESSION_LOGIN_USERID_KEY = "currentLoginUserId";
	public static final String SESSION_LOGIN_USERNAME_KEY = "currentLoginUserName";
	public static final String FREEMARKER_MAP = "freemarkerMap";
	public static final String ACTIVITY_ID = "activityId";
	private static final Log LOG = LogFactory.getLog(ControllerUtil.class);

	/**
	 * 获取当前请求url
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getCurrentUrl(HttpServletRequest request)
	{
		String url = request.getRequestURL().toString();
		String param = request.getQueryString();
		if (null != param)
		{
			return new StringBuffer(url).append("?").append(param).toString();
		}
		else
		{
			return url;
		}
	}

	public static void setCurrentLoginUserName(HttpServletRequest request, String accountId)
	{
		request.getSession().setAttribute(SESSION_LOGIN_USER_KEY, accountId);
	}

	public static String getCurrentLoginUserName(HttpServletRequest request)
	{
		String userName = null;
		if (request.getSession().getAttribute(SESSION_LOGIN_USER_KEY) != null)
		{
			userName = String.valueOf(request.getSession().getAttribute(SESSION_LOGIN_USER_KEY));
		}
		else
		{
			//			String[] cookSSn = CookieUtil.getSsnFromCookie(request);
			//			userName = cookSSn[1];
		}

		if (userName != null)
		{
			return userName.toLowerCase();
		}
		else
		{
			return userName;
		}
	}

}
