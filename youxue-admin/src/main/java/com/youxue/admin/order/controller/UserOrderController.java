package com.youxue.admin.order.controller;

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
import com.youxue.admin.order.constant.ConstantMapUtil;
import com.youxue.core.common.BaseController;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.vo.OrderDetailVo;
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
	@Resource
	private OrderService orderService;

	/***
	 *  用户个人订单信息查询
	 * @param request
	 * @param response
	 * @param pageNo 页数默认第一页
	 * @param orderType  订单类型，0 代表代付款订单 1代表待审核 2待出行订单 3已完成订单  4 代表已退款订单
	 * @return
	 */
	@RequestMapping(path = "/admin/userorder.do")
	public String userPageOrderInfo(HttpServletRequest request, HttpServletResponse response, String orderType,
			String orderId, String accountId, String campsName, String payType, String pageNo, ModelMap modelMap)
	{
		LOG.info("查询用户个人订单页 orderType=" + orderType + ",orderId=" + orderId + ",accountId=" + accountId + ",campsName="
				+ campsName + ",payType=" + payType + ",pageNo=" + pageNo);
		int status = -1;
		PayTypeEnum pType = PayTypeEnum.UNKNOW_PAY;
		try
		{
			if (StringUtils.isNotBlank(orderType))
			{
				status = Integer.parseInt(orderType);
			}
			if (StringUtils.isNotBlank(payType))
			{
				int tmpType = Integer.parseInt(payType);
				pType = PayTypeEnum.getByValue(tmpType);
			}
		}
		catch (Exception e)
		{
		}

		int pNum = Page.getPageNo(pageNo);
		Page<OrderDetailVo> page = new Page<OrderDetailVo>(pNum, Page.DEFAULT_PAGESIZE);

		page = orderDao.selectPageOrderListByInfo(page, status, pType, orderId, accountId, campsName);

		LOG.info("查询的结果为：resultList=" + page.getResultList());
		modelMap.put("page", page);
		modelMap.put("orderType", orderType);
		modelMap.put("orderId", orderId);
		modelMap.put("accountId", accountId);
		modelMap.put("campsName", campsName);
		modelMap.put("payType", pType.getValue());
		modelMap.put("orderStatusMap", ConstantMapUtil.orderStatusMap);
		modelMap.put("payTypeMap", ConstantMapUtil.payTypeMap);
		return "/order/orderList";

	}

	@RequestMapping(path = "/admin/auditOrder.do")
	@ResponseBody
	public String auditOrder(HttpServletRequest request, HttpServletResponse response, String type, String orderId,
			ModelMap modelMap)
	{
		LOG.info("查询用户个人订单页 type=" + type + ",orderId=" + orderId);
		if (StringUtils.isBlank(orderId))
		{
			modelMap.put("errorMessage", "订单Id不能为空");
			return "/error";
		}
		if ("1".equals(type))
		{
			OrderVo order = new OrderVo();
			order.setOrderId(orderId);
			order.setStatus(OrderVo.TO_OUT);
			order.setUpdateTime(DateUtil.getCurrentTimestamp());
			orderDao.updateByPrimaryKeySelective(order);
		}
		else if ("0".equals(type))
		{
			orderService.refundOrder(orderId);
		}
		else
		{
			modelMap.put("errorMessage", "审核状态非法,type=" + type);
			return "/error";
		}
		return "redirect:/admin/userorder.do";

	}
}
