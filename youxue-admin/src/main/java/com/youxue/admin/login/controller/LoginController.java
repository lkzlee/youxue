package com.youxue.admin.login.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lkzlee.pay.utils.DateUtil;
import com.youxue.admin.login.service.SysUserLoginLogService;
import com.youxue.admin.login.service.SysUserService;
import com.youxue.core.util.NetUtil;
import com.youxue.core.vo.SysUser;
import com.youxue.core.vo.SysUserLoginLog;

/**
 * @author www.inxedu.com
 *
 */
@Controller
public class LoginController
{
	private static final Log logger = LogFactory.getLog(LoginController.class);
	private static String loginPage = "/login";//后台登录页面
	private static String loginSuccess = "redirect:/main";//后台管理主界面

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserLoginLogService sysUserLoginLogService;

	@InitBinder(
	{ "sysUser" })
	public void initBinderSysUser(WebDataBinder binder)
	{
		binder.setFieldDefaultPrefix("sysUser.");
	}

	/**
	 * 后台用户退出登录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/outlogin")
	public String outLogin(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.getSession().removeAttribute("LOGIN_ADMIN_USER");

			request.getSession().invalidate();
		}
		catch (Exception e)
		{
			logger.error("outLogin()---error", e);
		}
		return "redirect:/login";
	}

	/**
	 * 进入登录页面
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping
	public String loginPage(HttpServletRequest request)
	{
		try
		{
		}
		catch (Exception e)
		{
			logger.error("loginPage()--error", e);
		}
		return loginPage;
	}

	/**
	 * 执行登录
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/login.do")
	public String login(HttpServletRequest request, HttpServletResponse response, SysUser sysUser, ModelMap modelMap)
	{
		try
		{
			if (sysUser.getLoginName() == null || sysUser.getLoginName().trim().equals(""))
			{
				modelMap.put("message", "请输入用户名!");
				return loginPage;
			}
			if (sysUser.getLoginPwd() == null || sysUser.getLoginPwd().trim().equals(""))
			{
				modelMap.put("message", "请输入密码!");
				return loginPage;
			}
			sysUser.setLoginPwd(sysUser.getLoginPwd());
			SysUser su = sysUserService.queryLoginUser(sysUser);
			if (su == null)
			{
				modelMap.put("message", "用户名或密码错误！");
				return loginPage;
			}
			//判断用户是否是可用状态
			if (su.getStatus() != 0)
			{
				modelMap.put("message", "该用户已经冻结！");
				return loginPage;
			}
			//缓存用登录信息
			String ip = NetUtil.getCurrentLoginUserIp(request);
			//修改用户登录记录
			sysUserService.updateUserLoginLog(su.getUserId(), DateUtil.getCurrentTimestamp(), ip);

			//添加登录记录
			SysUserLoginLog loginLog = new SysUserLoginLog();
			loginLog.setUserId(su.getUserId());//用户ID
			loginLog.setLoginTime(new Date());//
			loginLog.setIp(ip);//登录IP
			String userAgent = request.getHeader("User-Agent");
			if (StringUtils.isNotEmpty(userAgent))
			{
				loginLog.setUserAgent(userAgent.split(";")[0]);//浏览器
				loginLog.setOsName(userAgent.split(";")[1]);//操作系统
			}
			//保存登录日志
			sysUserLoginLogService.createLoginLog(loginLog);
			request.getSession().setAttribute("LOGIN_ADMIN_USER", su);
		}
		catch (Exception e)
		{
			modelMap.put("message", "系统繁忙，请稍后再操作！");
			logger.error("login()--error", e);
			return loginPage;
		}
		return loginSuccess;
	}
}
