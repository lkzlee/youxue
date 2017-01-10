package com.youxue.admin.sysuser.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.utils.DateUtil;
import com.youxue.admin.constant.AdminConstant;
import com.youxue.admin.login.service.SysUserService;
import com.youxue.admin.power.constant.PowerConstant;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.SysUserDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.SysUser;

/***
 * 用户个人订单页
 * @author lkzlee
 *
 */
@Controller
public class SysUserController extends BaseController
{
	private final static Log log = LogFactory.getLog(SysUserController.class);
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 系统挂管理员列表
	 * @param request
	 * @param response
	 * @param pageNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(path = "/sysUserList.do")
	public String sysUserList(HttpServletRequest request, HttpServletResponse response, String pageNo, String userName,
			String tel, String createTime, ModelMap modelMap)
	{
		LOG.info("查询系统挂管理员列表 pageNo=" + pageNo);
		int pNum = Page.getPageNo(pageNo);
		Page<SysUser> page = new Page<SysUser>(pNum, Page.DEFAULT_PAGESIZE);
		Date ctTime = null;
		if (StringUtils.isNotBlank(createTime))
		{
			ctTime = DateUtil.formatToDate(createTime, DateUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
		}
		page = sysUserDao.querySysUserPage(page, userName, tel);
		log.info("管理员列表，查询的结果集resultList=" + page.getResultList());
		modelMap.put("page", page);
		modelMap.put("userName", userName);
		modelMap.put("tel", tel);
		modelMap.put("createTime", createTime);
		modelMap.put("roleMap", PowerConstant.roleMap);
		return "/sysUser/sysUserList";

	}

	@RequestMapping(path = "/addSysUser.do")
	public String auditOrder(HttpServletRequest request, HttpServletResponse response, SysUser sysUser,
			ModelMap modelMap)
	{
		try
		{
			LOG.info("查询用户个人订单页 sysUser=" + sysUser);
			if (sysUser == null)
			{
				modelMap.put("errorMessage", "修改的系统用户为空");
				return "/error";
			}
			sysUser.setLoginPwd("123456");
			sysUserDao.createSysUser(sysUser);
		}
		catch (Exception e)
		{
			modelMap.put("errorMessage", "登录账户名已存在，请修改后提添加");
			return "/error";
		}
		return "redirect:/sysUserList.do";

	}

	/***
	 * type=1 启用
	 * type=2 禁用
	 * type=3 删除
	 * @param request
	 * @param response
	 * @param userId
	 * @param type
	 * @return
	 */
	@RequestMapping(path = "/forbidOrStartSysUser.do")
	@ResponseBody
	public String forbidOrStartSysUser(HttpServletRequest request, HttpServletResponse response, String userId,
			String type)
	{
		try
		{
			LOG.info("禁用或启用用户 sysuser=" + userId);

			if (StringUtils.isBlank(userId))
			{

				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			}
			Integer uId = Integer.parseInt(userId);
			int tp = Integer.parseInt(type);
			sysUserService.updateDisableOrstartUser(uId, tp);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("置位成功"));

		}
		catch (Exception e)
		{
			LOG.error("禁用用户失败 userId=" + userId);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后"));
		}
	}

	@RequestMapping(path = "/resetSysUser.do")
	@ResponseBody
	public String resetPassword(HttpServletRequest request, HttpServletResponse response, String userId)
	{
		try
		{
			LOG.info("重置用户密码 userId=" + userId);

			if (StringUtils.isBlank(userId))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			}
			Integer uId = Integer.parseInt(userId);
			SysUser sysUser = new SysUser();
			sysUser.setUserId(uId);
			sysUser.setLoginPwd("123456");
			sysUserService.updateUserPwd(sysUser);
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("重置成功"));

		}
		catch (Exception e)
		{
			LOG.error("禁用用户失败 userId=" + userId);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后"));
		}
	}

	@RequestMapping(path = "/sysUserModify.do")
	public String updateSysUser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
	{
		SysUser loginUser = (SysUser) request.getSession().getAttribute(AdminConstant.CURRENT_USER);
		modelMap.put("sysUser", loginUser);
		return "/sysUser/modifySysUser";
	}

	@RequestMapping(path = "/updateSysUser.do")
	public String updateSysUser(HttpServletRequest request, HttpServletResponse response, SysUser sysUser,
			String newPassword, ModelMap modelMap)
	{
		try
		{
			LOG.info("修改账户信息 sysUser=" + sysUser);

			SysUser loginUser = (SysUser) request.getSession().getAttribute(AdminConstant.CURRENT_USER);
			if (loginUser == null)
			{
				modelMap.put("msg", "当前用户未登录，请重新登录");
				return "/sysUser/modifySysUser";
			}
			if (sysUser == null)
			{
				modelMap.put("msg", "参数非法");
				return "/sysUser/modifySysUser";
			}
			sysUser.setUserId(loginUser.getUserId());
			sysUser.setLoginName(loginUser.getLoginName());
			sysUser = sysUserService.queryLoginUser(sysUser);
			if (sysUser == null)
			{
				modelMap.put("msg", "密码不正确");
				return "/sysUser/modifySysUser";
			}
			sysUser.setLoginPwd(newPassword);
			sysUserService.updateUserPwd(sysUser);
			request.getSession().setAttribute(AdminConstant.CURRENT_USER, sysUser);
			modelMap.put("sysUser", sysUser);
			modelMap.put("msg", "修改成功");
			return "/sysUser/modifySysUser";

		}
		catch (Exception e)
		{
			LOG.error("账户修改sysUser=" + sysUser);
			modelMap.put("msg", "系统繁忙，请稍后");
			return "/sysUser/modifySysUser";
		}
	}
}
