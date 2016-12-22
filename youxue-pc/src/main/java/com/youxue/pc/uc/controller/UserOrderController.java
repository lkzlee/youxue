package com.youxue.pc.uc.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.OrderDetailVo;
import com.youxue.core.vo.Page;
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
		Page<OrderDetailVo> page = new Page<OrderDetailVo>(pNum, Page.DEFAULT_PAGESIZE);
		page = orderDao.selectPageOrderListByType(page, status, accountId);
		Page<OrderItemDto> resultPage = new Page<OrderItemDto>(pNum, Page.DEFAULT_PAGESIZE);
		List<OrderItemDto> resultList = translatePageList(page);
		resultPage.setTotalCount((int) page.getTotalCount());
		resultPage.setResult(100);
		resultPage.setResultList(resultList);
		resultPage.setResultDesc("查询成功");
		return JsonUtil.serialize(resultPage);

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
			for (Entry<String, List<OrderDetailVo>> en : map.entrySet())
			{
				OrderItemDto orderItemDto = new OrderItemDto();
				OrderDetailVo orderVo = en.getValue().get(0);
				orderItemDto.setLogicOrderId(orderVo.getLogicOrderId());
				orderItemDto.setPayStatus(orderVo.getPayStatus());
				orderItemDto.setOrderList(en.getValue());
				resultList.add(orderItemDto);
			}
		}
		return resultList;
	}
}
