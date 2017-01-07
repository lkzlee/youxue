package com.youxue.admin.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youxue.core.common.BaseController;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.UserInfoVo;

/***
 * 用户列表list
 * @author lkzlee
 *
 */
@Controller
public class UserListController extends BaseController
{
	private final static Log log = LogFactory.getLog(UserListController.class);
	@Resource
	private UserInfoDao userInfoDao;

	/***
	 *  用户个人订单信息查询
	 * @param request
	 * @param response
	 * @param pageNo 页数默认第一页
	 * @param orderType  订单类型，0 代表代付款订单 1代表待审核 2待出行订单 3已完成订单  4 代表已退款订单
	 * @return
	 */
	@RequestMapping(path = "/admin/userList.do")
	public String userList(HttpServletRequest request, HttpServletResponse response, String accountId, String nickName,
			String pageNo, ModelMap modelMap)
	{
		try
		{
			LOG.info("查询用户列表 accountId=" + accountId + ",nickName=" + nickName + ",pageNo=" + pageNo);
			int pNum = Page.getPageNo(pageNo);
			Page<UserInfoVo> page = new Page<UserInfoVo>(pNum, Page.DEFAULT_PAGESIZE);
			page = userInfoDao.selectPageUserInfoListByInfo(page, accountId, nickName);
			LOG.info("查询用户列表的结果为：resultList=" + page.getResultList());
			modelMap.put("page", page);
			modelMap.put("accountId", accountId);
			modelMap.put("nickName", nickName);
			return "/userInfo/userList";
		}
		catch (Exception e)
		{
			log.fatal("查询用户列表，系统异常，请检查", e);
			modelMap.put("errorMessage", "系统繁忙，请稍后");
			return "/error";
		}

	}
}
