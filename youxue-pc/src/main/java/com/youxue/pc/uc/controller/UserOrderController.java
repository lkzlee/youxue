package com.youxue.pc.uc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.OrderVo;
import com.youxue.core.vo.Page;

/***
 * 用户个人订单页
 * @author lkzlee
 *
 */
@Controller
public class UserOrderController extends BaseController
{
	private final static Log LOG = LogFactory.getLog(UserOrderController.class);
	@Resource
	private OrderDao orderDao;

	/***
	 *  用户个人订单信息查询
	 * @param request
	 * @param response
	 * @param pageNo 页数默认第一页
	 * @param orderType  订单类型，0 代表代付款订单 1代表待审核 2待出行订单 3已完成订单  4 代表已退款订单
	 * @return
	 */
	@RequestMapping(path = "/uc/userorder.do")
	@ResponseBody
	public String userPageOrderInfo(HttpServletRequest request, HttpServletResponse response, String pageNo,
			String orderType)
	{
		String accountId = getCurrentLoginUserName(request);
		LOG.info("查询用户个人订单页，accountId=" + accountId + ",orderType=" + orderType);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		int status = 0;
		try
		{
			if (StringUtils.isNotBlank(orderType))
			{
				status = Integer.parseInt(orderType);
			}
		}
		catch (Exception e)
		{
		}
		int pNum = Page.getPageNo(pageNo);
		Page<OrderVo> page = new Page<OrderVo>(pNum, Page.DEFAULT_PAGESIZE);

		page = orderDao.selectPageOrderListByType(page, status, accountId);
		return JsonUtil.serialize(page);

	}
}
