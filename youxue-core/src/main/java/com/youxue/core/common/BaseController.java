package com.youxue.core.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.youxue.core.util.ControllerUtil;
import com.youxue.core.util.NetUtil;

/**
 * 所有Controller都需要的操作
 * 
 * @author luming
 */
@Controller
public class BaseController
{
	protected final static Log LOG = LogFactory.getLog(BaseController.class);
	public final static String ERROR_PAGE = "error";//error.ftl

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e)
	{
		if (e instanceof IOException)
		{
			LOG.info("Class Exception:" + this.getClass() + "|ExceptionHandler info:" + e.getMessage(), e);
		}
		else
		{
			LOG.error("Class Exception:" + this.getClass() + "|ExceptionHandler info:" + e.getMessage(), e);
		}
		return "/50x";
	}

	/**
	 * 获取当前用户名
	 * @return
	 */
	public String getCurrentLoginUserName(HttpServletRequest request)
	{
		return ControllerUtil.getCurrentLoginUserName(request, request.getSession());
	}

	/**
	 * 获取当前登录ip
	 * @param request
	 * @return
	 */
	protected String getCurrentLoginUserIp(HttpServletRequest request)
	{
		return NetUtil.getCurrentLoginUserIp(request);
	}

}
