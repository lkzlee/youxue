package com.youxue.admin.order.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.lkzlee.pay.bean.WeiXinConfigBean;
import com.lkzlee.pay.constant.ConfigConstant;
import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.utils.CommonUtil;
import com.lkzlee.pay.utils.DateUtil;
import com.lkzlee.pay.wx.bean.TemplateMsgDataDto;
import com.lkzlee.pay.wx.helper.MessageHelper;
import com.youxue.admin.constant.ConstantMapUtil;
import com.youxue.admin.order.controller.dto.OrderDetailInfoDto;
import com.youxue.core.common.BaseController;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.dao.OrderPersonDao;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.enums.MessageEnum;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.service.message.MessageService;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.service.order.RefundService;
import com.youxue.core.util.MailUtil;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.OrderDetailVo;
import com.youxue.core.vo.OrderPersonVo;
import com.youxue.core.vo.OrderVo;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.UserInfoVo;

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
	@Resource
	private CampsDao campsDao;
	@Resource
	private RefundService refundService;
	@Resource
	private MessageService messageService;
	@Resource
	private MailUtil mailUtil;
	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private OrderPersonDao orderPersonDao;
	@Resource
	private LogicOrderDao logicOrderDao;

	/***
	 * 个人订单详情
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping(path = "/admin/orderdetail.do")
	public String orderDetailInfo(HttpServletRequest request, HttpServletResponse response, String orderId,
			ModelMap modelMap)
	{
		try
		{
			LOG.info("管理后台订单详情页,orderId=" + orderId);
			if (StringUtils.isBlank(orderId))
			{
				modelMap.put("errorMessage", "参数非法");
				return "/error";
			}
			OrderVo order = orderDao.selectByPrimaryKey(orderId, false);
			if (order == null)
			{
				modelMap.put("errorMessage", "订单不存在");
				return "/error";
			}
			CampsVo camps = campsDao.selectByPrimaryKey(order.getCampsId());
			LogicOrderVo logicOrder = logicOrderDao.selectByPrimaryKey(order.getLogicOrderId(), false);
			List<OrderPersonVo> list = orderPersonDao.getOrderPersonById(order.getOrderId());
			OrderDetailInfoDto orderDetail = buildOrderDetailInfo(order, logicOrder, camps, list);
			modelMap.put("orderDetail", orderDetail);
			modelMap.put("payTypeMap", ConstantMapUtil.payTypeMap);
			return "/order/orderDetail";
		}
		catch (Exception e)
		{
			LOG.fatal("订单详情查询异常", e);
			modelMap.put("errorMessage", "系统繁忙，请稍后");
			return "/error";
		}

	}

	private OrderDetailInfoDto buildOrderDetailInfo(OrderVo order, LogicOrderVo logicOrder, CampsVo camps,
			List<OrderPersonVo> list)
	{
		OrderDetailInfoDto orderDto = new OrderDetailInfoDto();
		orderDto.setPayType(logicOrder.getPayType());
		BeanUtils.copyProperties(camps, orderDto);
		BeanUtils.copyProperties(order, orderDto);
		orderDto.setOrderPersonList(list);
		orderDto.setResult(100);
		orderDto.setResultDesc("查询成功");
		return orderDto;
	}

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

	@RequestMapping(path = "/admin/refundOrder.do")
	public String auditOrder(HttpServletRequest request, HttpServletResponse response, String orderId, ModelMap modelMap)
	{
		LOG.info("查询用户个人订单页 ,orderId=" + orderId);
		if (StringUtils.isBlank(orderId))
		{
			modelMap.put("errorMessage", "订单Id不能为空");
			return "/error";
		}
		OrderVo order = orderDao.selectByPrimaryKey(orderId, false);
		if (order.getStatus() != OrderVo.CANCEL)
		{
			modelMap.put("errorMessage", "订单状态不正确，不能退款");
			return "/error";
		}
		Object result = refundService.refundRequest(orderId);
		if (result instanceof String)
		{
			return "redirect:" + result;
		}
		return "redirect:/admin/userorder.do";

	}

	@RequestMapping(path = "/admin/auditOrder.do")
	public String auditOrder(HttpServletRequest request, HttpServletResponse response, String type, String orderId,
			ModelMap modelMap)
	{
		LOG.info("查询用户个人订单页 type=" + type + ",orderId=" + orderId);
		if (StringUtils.isBlank(orderId))
		{
			modelMap.put("errorMessage", "订单Id不能为空");
			return "/error";
		}
		OrderVo order = orderDao.selectByPrimaryKey(orderId, false);
		order.setUpdateTime(DateUtil.getCurrentTimestamp());
		if ("pass".equals(type))
		{
			setOrderStatus(order, true);
		}
		else if ("fail".equals(type))
		{
			setOrderStatus(order, false);
		}
		else
		{
			modelMap.put("errorMessage", "审核状态非法,type=" + type);
			return "/error";
		}
		if (OrderVo.CANCEL == order.getStatus())
		{
			Object result = refundService.addRefund(orderId);
			if (result instanceof String)
			{
				return "redirect:" + result;
			}
		}
		else
		{
			orderDao.updateByPrimaryKeySelective(order);
		}
		return "redirect:/admin/userorder.do";

	}

	private void setOrderStatus(OrderVo order, boolean isPass)
	{
		if (isPass)
		{
			/***
			 * 支付成功--->待出行
			 */
			if (OrderVo.PAY == order.getStatus())
			{
				order.setStatus(OrderVo.TO_OUT);
				messageService.addOrderMessage(MessageEnum.WAIT_DEPARTURE, order.getAccountId(), order.getOrderId());
				UserInfoVo userInfo = userInfoDao.selectByPrimaryKey(order.getAccountId());
				if (userInfo != null && StringUtils.isNotBlank(userInfo.getEmail()))
				{
					mailUtil.init(userInfo.getEmail(), null);
					mailUtil.sendEmail(
							"【营联天下】您有一条待出行订单",
							"【营联天下】您有一条待出行订单",
							"您有一条待出行订单，订单详情请查看：http://qg.igalaxy.com.cn/user_paymentInfo.jsp?orderId="
									+ order.getOrderId(), "UTF-8");

				}
				pushWxMsg(order, userInfo);

			}
			/***
			 * 待出行---> 已完成
			 */
			else if (OrderVo.TO_OUT == order.getStatus())
			{
				order.setStatus(OrderVo.DONE);
				UserInfoVo user = userInfoDao.selectByPrimaryKey(order.getAccountId());
				BigDecimal spend = user.getSpend() == null ? BigDecimal.ZERO : user.getSpend();
				user.setSpend(spend.add(order.getPayPrice()));
				userInfoDao.updateByPrimaryKeySelective(user);
			}
			/***
			 * 申请退款-->已退款,
			 */
			else if (OrderVo.APPLY_REFUND == order.getStatus())
			{
				order.setStatus(OrderVo.CANCEL);
				messageService.addOrderMessage(MessageEnum.REFUNDED, order.getAccountId(), order.getOrderId());
			}
			/***
			 * 退款失败---->申请退款
			 */
			else if (OrderVo.APPLY_FAILED == order.getStatus())
			{
				order.setStatus(OrderVo.APPLY_REFUND);
			}
			else
			{
				throw new BusinessException("订单状态在该流程中非法：status=" + order.getStatus());
			}

		}
		else
		{
			/***
			 * 支付成功--->申请退款，待出行--->申请退款
			 */
			if (OrderVo.PAY == order.getStatus() || OrderVo.TO_OUT == order.getStatus())
			{
				order.setStatus(OrderVo.APPLY_REFUND);
				//				messageService.addOrderMessage(MessageEnum.AUDIT_FAILED, order.getAccountId(), order.getOrderId());
			}
			/***
			 * 申请退款-->不通过，退款失败,
			 */
			else if (OrderVo.APPLY_REFUND == order.getStatus())
			{
				order.setStatus(OrderVo.APPLY_FAILED);
			}
			else
			{
				throw new BusinessException("订单状态在该流程中非法：status=" + order.getStatus());
			}
		}
	}

	private void pushWxMsg(OrderVo order, UserInfoVo userInfo)
	{
		try
		{
			if (userInfo != null && StringUtils.isNotBlank(userInfo.getOpenId()))
			{
				String openId = userInfo.getOpenId();
				String templateId = WeiXinConfigBean.getPayConfigValue(ConfigConstant.WEIXIN_MSG_TEMPLATE_ID);
				String[] templates = templateId.split(";");
				for (String t : templates)
				{
					if (t.indexOf("noitfy_msg") >= 0)
					{
						templateId = t.split(":")[1];
					}
				}
				CampsVo campsVo = campsDao.selectByPrimaryKey(order.getCampsId());
				TemplateMsgDataDto data = new TemplateMsgDataDto(openId, templateId,
						"http://qg.igalaxy.com.cn/wxwap/order_info.jsp?orderId=" + order.getOrderId());
				data.push("first", "尊敬的用户，您的订单已经支付成功");
				data.push("orderId", order.getOrderId());
				data.push("orderPrice", CommonUtil.formatBigDecimal(order.getPayPrice()));
				data.push("orderStatus", "待出行");
				data.push("productName", campsVo.getCampsName());
				data.push("remark", "如有疑问，请拨打咨询热线123323");
				JSONObject result = MessageHelper.templateSend(data);
				LOG.info("【wx push msg】推送微信消息结果 result=" + result);
			}
		}
		catch (Exception e)
		{
			LOG.fatal("【wx push msg审核消息】推送微信消息异常 order=" + order, e);
		}
	}
}
