package com.youxue.pc.order.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.utils.CommonUtil;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CouponCodeVo;
import com.youxue.core.vo.OrderPersonVo;
import com.youxue.pc.order.dto.AddTradeItemDto;
import com.youxue.pc.order.dto.AddTradeOrderDto;
import com.youxue.pc.order.service.AddOrderPayService;

/**
 * 订单相关处理
 * @author lkzlee
 *
 */
@Controller
public class OrderController extends BaseController
{
	protected final static Log LOG = LogFactory.getLog(OrderController.class);
	@Autowired
	JedisProxy jedisProxy;
	@Resource
	private AddOrderPayService addOrderPayService;
	@Resource
	private CouponCodeDao couponCodeDao;
	@Resource
	private CampsDao campsDao;

	/***
	 * 下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/addTradeOrder.do", method = RequestMethod.POST)
	@ResponseBody
	public String addTrade(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AddTradeOrderDto orderData)
	{
		try
		{
			String ip = getCurrentLoginUserIp(request);
			String accountId = getCurrentLoginUserName(request);
			checkIfParamValidAndFillBaseInfo(orderData, accountId);
			BaseResponseDto responseDto = addOrderPayService.addTradeOrderService(orderData, ip, accountId);
			return JsonUtil.serialize(responseDto);
		}
		catch (BusinessException e)
		{
			LOG.error("下单参数校验及流程处理，orderData=" + orderData + ",msg:" + e.getMessage());
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc(e.getMessage()));
		}
		catch (Exception e)
		{
			LOG.error("下单处理流程异常，orderData=" + orderData + ",msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后！"));
		}
	}

	private void checkIfParamValidAndFillBaseInfo(AddTradeOrderDto orderData, String accountId)
	{
		if (StringUtils.isEmpty(accountId))
			throw new BusinessException("用户未登录，请检查");
		if (orderData == null || orderData.getPayType() == null)
			throw new BusinessException("参数非法");
		PayTypeEnum payType = PayTypeEnum.getByValue(orderData.getPayType());
		if (payType != PayTypeEnum.ALIPAY && payType != PayTypeEnum.WEIXIN_APY)
			throw new BusinessException("非法支付方式");
		if (ArrayUtils.isEmpty(orderData.getOrderList()))
			throw new BusinessException("无任何订单信息");
		/***
		 * 校验下单的人数和 出行人人数，校验手机号、email合法性，校验支付方式，校验优惠券合法性
		 */
		AddTradeItemDto orderItemList[] = orderData.getOrderList();
		for (AddTradeItemDto ote : orderItemList)
		{
			int totalPerson = ote.getTotalCount();
			if (totalPerson <= 0)
				throw new BusinessException("对应订单提交的出行人数非法，请检查");
			OrderPersonVo personList[] = ote.getOutPersonList();
			if (ArrayUtils.isEmpty(personList))
				throw new BusinessException("对应的订单没有出现人信息，请检查");
			if (totalPerson != personList.length)
				throw new BusinessException("对应订单提交的人数和出行人数不符");
			CouponCodeVo coupon = couponCodeDao.selectCouponByCode(ote.getCodeId(), false);
			if (coupon == null || coupon.getStatus() != CouponCodeVo.NORMAL)
			{
				throw new BusinessException("优惠券使用有误，对应的优惠券不存在");
			}

			if (CommonUtil.isValidEmail(ote.getContactPhone()))
			{
				throw new BusinessException("订单对应联系人的电话有误，请检查");
			}
			CampsVo camps = campsDao.selectByPrimaryKey(ote.getCampsId());
			if (camps == null || camps.getStatus() != CampsVo.NORMAL)
			{
				throw new BusinessException("下单有误，对应的营地不存在或者营地未开放");
			}
			if (!coupon.getCategoryIds().contains(camps.getCampsSubjectId()))
			{
				throw new BusinessException("下单有误，改优惠券不适用于该营地，请检查");
			}
			BigDecimal couponPrice = coupon.getCodeAmount().multiply(new BigDecimal(totalPerson));
			BigDecimal totalPrice = camps.getTotalPrice().multiply(new BigDecimal(totalPerson));
			BigDecimal totalPayPrice = totalPrice.subtract(couponPrice);
			if (BigDecimal.ZERO.compareTo(totalPayPrice) > 0)
				throw new BusinessException("提交的订单优惠券大于支付金额，有误，请检查");
			ote.setPayPrice(totalPayPrice);
			ote.setTotalPrice(totalPrice);
			ote.setCodePrice(couponPrice);
		}

	}
}
