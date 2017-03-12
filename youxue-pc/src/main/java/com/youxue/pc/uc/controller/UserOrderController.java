package com.youxue.pc.uc.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.dao.OrderPersonDao;
import com.youxue.core.dao.ProductOrderVoDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.OrderDetailVo;
import com.youxue.core.vo.OrderPersonVo;
import com.youxue.core.vo.OrderVo;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.ProductOrderVo;
import com.youxue.pc.uc.dto.OrderDetailInfoDto;
import com.youxue.pc.uc.dto.OrderItemDto;

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
	private CampsDao campsDao;
	@Resource
	private OrderPersonDao orderPersonDao;
	@Resource
	private LogicOrderDao logicOrderDao;
	@Resource
	private ProductOrderVoDao productOrderVoDao;

	/***
	 *  用户个人订单信息查询
	 * @param request
	 * @param response
	 * @param pageNo 页数默认第一页
	 * @param orderType  订单类型，-1 全部订单 0 代表代付款订单 1代表待审核 2待出行订单 3已完成订单  4 代表已退款订单
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
		List<Integer> statusList = Lists.newArrayList();
		try
		{
			int status = -1;
			if (StringUtils.isNotBlank(orderType))
			{
				status = Integer.parseInt(orderType);
			}
			if (status == OrderVo.DELETED)
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			}

			if (status == OrderVo.TO_OUT)
			{
				statusList.add(OrderVo.TO_OUT);
				statusList.add(OrderVo.APPLY_REFUND);
				statusList.add(OrderVo.APPLY_FAILED);
			}
			else if (status != -1)
			{
				statusList.add(status);
			}
		}
		catch (Exception e)
		{
		}
		int pNum = Page.getPageNo(pageNo);
		Page<OrderDetailVo> page = new Page<OrderDetailVo>(pNum, Page.DEFAULT_PAGESIZE);
		page = orderDao.selectPageOrderListByType(page, statusList, accountId);
		Page<OrderItemDto> resultPage = new Page<OrderItemDto>(pNum, Page.DEFAULT_PAGESIZE);
		List<OrderItemDto> resultList = translatePageList(page);
		resultPage.setTotalCount((int) page.getTotalCount());
		resultPage.setResult(100);
		resultPage.setResultList(resultList);
		resultPage.setResultDesc("查询成功");
		return JsonUtil.serialize(resultPage);

	}

	@RequestMapping(path = "/uc/deleteorder.do")
	@ResponseBody
	public String deleteOrderInfo(HttpServletRequest request, HttpServletResponse response, String orderId)
	{
		String accountId = getCurrentLoginUserName(request);
		LOG.info("删除订单，accountId=" + accountId + ",orderId=" + orderId);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (StringUtils.isBlank(orderId))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
		}
		OrderVo order = orderDao.selectByPrimaryKey(orderId, false);
		if (order == null || !accountId.equals(order.getAccountId()))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("订单不存在"));
		}
		//		if (order.getStatus() != OrderVo.UNPAY && order.getStatus() != OrderVo.DELETED
		//				&& order.getStatus() != OrderVo.DONE)
		//		{
		//			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("订单状态不正确，不能删除该订单"));
		//		}
		order.setStatus(OrderVo.DELETED);
		order.setUpdateTime(DateUtil.getCurrentTimestamp());
		int success = orderDao.updateByPrimaryKeySelective(order);
		if (success == 1)
			return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("删除成功"));
		return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("删除失败"));

	}

	@RequestMapping(path = "/uc/cancelorder.do")
	@ResponseBody
	public String cancelOrderInfo(HttpServletRequest request, HttpServletResponse response, String orderId)
	{
		String accountId = getCurrentLoginUserName(request);
		LOG.info("取消订单，退款申请，accountId=" + accountId + ",orderId=" + orderId);
		if (StringUtils.isBlank(accountId))
			return JsonUtil.serialize(BaseResponseDto.notLoginDto());
		if (StringUtils.isBlank(orderId))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
		}
		OrderVo order = orderDao.selectByPrimaryKey(orderId, false);
		if (order == null || !accountId.equals(order.getAccountId()))
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("订单不存在"));
		}
		if (order.getStatus() == OrderVo.PAY || order.getStatus() == OrderVo.TO_OUT
				|| order.getStatus() == OrderVo.APPLY_FAILED)
		{
			order.setStatus(OrderVo.APPLY_REFUND);
			order.setUpdateTime(DateUtil.getCurrentTimestamp());
			int success = orderDao.updateByPrimaryKeySelective(order);
			if (success == 1)
				return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("取消成功，等待审核退款"));
		}
		else if (order.getStatus() == OrderVo.APPLY_REFUND)
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("订单已经取消，请耐心等待审核退款"));
		}
		else
		{
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("订单状态不正确，不能取消该订单"));
		}
		return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("取消订单失败,请重新操作"));

	}

	@RequestMapping(path = "/uc/orderdetail.do")
	@ResponseBody
	public String orderDetailInfo(HttpServletRequest request, HttpServletResponse response, String orderId)
	{
		try
		{
			String accountId = getCurrentLoginUserName(request);
			LOG.info("订单详情页，accountId=" + accountId + ",orderId=" + orderId);
			if (StringUtils.isBlank(accountId))
				return JsonUtil.serialize(BaseResponseDto.notLoginDto());
			if (StringUtils.isBlank(orderId))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			}
			OrderVo order = orderDao.selectByPrimaryKey(orderId, false);
			if (order == null || !accountId.equals(order.getAccountId()))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("订单不存在"));
			}
			CampsVo camps = campsDao.selectByPrimaryKey(order.getCampsId());
			LogicOrderVo logicOrder = logicOrderDao.selectByPrimaryKey(order.getLogicOrderId(), false);
			List<OrderPersonVo> list = orderPersonDao.getOrderPersonById(order.getOrderId());
			OrderDetailInfoDto orderDetail = buildOrderDetailInfo(order, logicOrder, camps, list);
			return JsonUtil.serialize(orderDetail);
		}
		catch (Exception e)
		{
			LOG.fatal("订单详情查询异常", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后"));
		}

	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 周边产品订单列表
	 */
	@RequestMapping("/productOrderList.do")
	@ResponseBody
	public String productOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			String accountId = getCurrentLoginUserName(request);
			if (StringUtils.isBlank(accountId))
				return JsonUtil.serialize(BaseResponseDto.notLoginDto());
			List<ProductOrderVo> productOrderList1 = productOrderVoDao.selectByBuyType(accountId, 1);
			List<ProductOrderVo> productOrderList2 = productOrderVoDao.selectByBuyType(accountId, 2);
			Map<String, Object> maps = Maps.newHashMap();
			maps.put("result", "100");
			maps.put("desc", "success");
			maps.put("productOrderList1", productOrderList1);
			maps.put("productOrderList2", productOrderList2);
			return JsonUtil.mapToJson(maps);
		}
		catch (Exception e)
		{
			LOG.fatal("error in productOrderList，msg:", e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("请求异常"));
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

	private List<OrderItemDto> translatePageList(Page<OrderDetailVo> page)
	{
		List<OrderItemDto> resultList = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(page.getResultList()))
		{
			Map<String, List<OrderDetailVo>> map = Maps.newHashMap();
			for (OrderDetailVo ov : page.getResultList())
			{
				if (map.containsKey(ov.getLogicOrderId()))
				{
					List<OrderDetailVo> list = map.get(ov.getLogicOrderId());
					list.add(ov);
				}
				else
				{
					List<OrderDetailVo> list = Lists.newArrayList();
					list.add(ov);
					map.put(ov.getLogicOrderId(), list);
				}
			}
			for (OrderDetailVo ov : page.getResultList())
			{
				OrderItemDto orderItemDto = new OrderItemDto();
				orderItemDto.setLogicOrderId(ov.getLogicOrderId());
				orderItemDto.setPayStatus(ov.getPayStatus());
				List<OrderDetailVo> list = map.get(ov.getLogicOrderId());
				Collections.sort(list, new Comparator<OrderDetailVo>() {
					@Override
					public int compare(OrderDetailVo o1, OrderDetailVo o2)
					{
						return o2.getCreatTime().compareTo(o1.getCreatTime());
					}
				});
				orderItemDto.setOrderList(list);
				resultList.add(orderItemDto);
			}
		}
		return resultList;
	}
}
