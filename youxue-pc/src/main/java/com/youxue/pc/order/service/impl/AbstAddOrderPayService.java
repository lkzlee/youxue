package com.youxue.pc.order.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkzlee.pay.bean.AlipayConfigBean;
import com.lkzlee.pay.bean.WeiXinConfigBean;
import com.lkzlee.pay.constant.ConfigConstant;
import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.service.PayService;
import com.lkzlee.pay.third.alipay.dto.request.AliPayOrderDto;
import com.lkzlee.pay.third.dto.AbstThirdPayDto;
import com.lkzlee.pay.third.weixin.dto.request.WeiXinOrderDto;
import com.lkzlee.pay.utils.CommonUtil;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.service.order.dto.AddTradeOrderDto;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.OrderVo;
import com.youxue.pc.order.service.AddOrderPayService;

public abstract class AbstAddOrderPayService implements AddOrderPayService
{
	protected final static Log LOG = LogFactory.getLog(AbstAddOrderPayService.class);
	@Resource(name = "weiXinOrderPayService")
	protected PayService weiXinPayService;
	@Resource(name = "aliPayOrderPayService")
	protected PayService aliPayService;
	@Resource
	protected OrderService orderService;
	@Resource
	protected OrderDao orderDao;
	@Resource
	protected LogicOrderDao logicOrderDao;
	@Resource
	protected CampsDao campsDao;
	@Autowired
	protected JedisProxy jedisProxy;

	protected abstract BaseResponseDto parseOrderParam(Object param, String accountId, String logicOrderId);

	protected abstract AbstThirdPayDto buildThirdPayOrderByLogicOrderId(String logicOrderId, boolean isDel,
			String openId);

	protected abstract BaseResponseDto paramContinuePayPara(String accountId, String logicOrderId);

	/***
	 * 下单总体流程处理
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public BaseResponseDto addTradeOrderService(AddTradeOrderDto orderData, String ip, String accountId)
	{
		try
		{
			LOG.info("@@开始下单，参数accountId=" + accountId + ",ip=" + ip + ",orderData=" + orderData);
			/***
			 * 插入数据库订单数据
			 */
			String logicOrderId = orderService.addOrder(orderData, ip, accountId);
			int payType = Integer.parseInt(orderData.getPayType().trim());
			PayService payService = getPayService(payType);
			AbstThirdPayDto thirdPayDto = buildThirdPayOrderByLogicOrderId(logicOrderId, true, orderData.getOpenId());
			LOG.info("@@构造下单的参数为：logicOrderId=" + logicOrderId + ",thirdPayDto=" + thirdPayDto);

			/**
			 * 向第三方下单
			 */
			Object param = payService.addThirdPayOrderService(thirdPayDto);
			/***
			 * 解析构造下单结果，并返回
			 */
			BaseResponseDto responseDto = parseOrderParam(param, accountId, logicOrderId);
			LOG.info("@@下单返回，logicOrderId=" + logicOrderId + ",responseDto=" + responseDto);
			return responseDto;
		}
		catch (BusinessException e)
		{
			LOG.fatal("系统执行出现错误，请检查，msg：" + e.getMessage(), e);
			return BaseResponseDto.errorDto().setDesc(e.getMessage());
		}
		catch (Exception e)
		{
			LOG.fatal("系统异常，请检查，msg：" + e.getMessage(), e);
			return BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后");
		}
	}

	@Override
	public BaseResponseDto addTradeOrderServiceById(String logicOrderId, String ip, String accountId, String openId)
	{
		try
		{
			LOG.info("@@支付购买，参数accountId=" + accountId + ",ip=" + ip + ",logicOrderId=" + logicOrderId);
			LogicOrderVo logicOrderVo = logicOrderDao.selectByPrimaryKey(logicOrderId, false);
			PayService payService = getPayService(logicOrderVo.getPayType());
			if (PayTypeEnum.WEIXIN_PAY.getValue() == logicOrderVo.getPayType()
					|| PayTypeEnum.WEIXIN_JS_API.getValue() == logicOrderVo.getPayType())
			{
				return paramContinuePayPara(accountId, logicOrderId);
			}
			AbstThirdPayDto thirdPayDto = buildThirdPayOrderByLogicOrderId(logicOrderId, false, openId);
			LOG.info("@@支付购买的参数为：logicOrderId=" + logicOrderId + ",thirdPayDto=" + thirdPayDto);

			/**
			 * 向第三方下单
			 */
			Object param = payService.addThirdPayOrderService(thirdPayDto);
			/***
			 * 解析构造下单结果，并返回
			 */
			BaseResponseDto responseDto = parseOrderParam(param, accountId, logicOrderId);
			LOG.info("@@支付购买，下单返回，logicOrderId=" + logicOrderId + ",responseDto=" + responseDto);
			return responseDto;
		}
		catch (BusinessException e)
		{
			LOG.fatal("系统执行出现错误，请检查，msg：" + e.getMessage(), e);
			return BaseResponseDto.errorDto().setDesc(e.getMessage());
		}
		catch (Exception e)
		{
			LOG.fatal("系统异常，请检查，msg：" + e.getMessage(), e);
			return BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后");
		}
	}

	protected WeiXinOrderDto buildWeiXinPayDto(LogicOrderVo logicOrderVo, List<OrderVo> orderList, String openId)
	{
		WeiXinOrderDto payResultDto = new WeiXinOrderDto();
		//		payResultDto.setAttach(attach);
		payResultDto.setBody("游学营地活动");
		//		payResultDto.setDetail(detail);
		payResultDto.setFee_type("CNY");
		//		payResultDto.setGoods_tag(goods_tag);
		//		payResultDto.setLimit_pay(limit_pay);
		String notfiyUrl = WeiXinConfigBean.getPayConfigValue(ConfigConstant.WEIXIN_PAY_NOTIFY_URL);
		payResultDto.setNotify_url(notfiyUrl);

		payResultDto.setOut_trade_no(logicOrderVo.getLogicOrderId());

		payResultDto.setSpbill_create_ip(logicOrderVo.getOrderIp());

		Date expireTime = DateUtil.getIntervalSeconds(DateUtil.getCurrentTimestamp(), 7200);
		payResultDto.setTime_start(DateUtil.formatDate(logicOrderVo.getCreateTime(), "yyyyMMddHHmmss"));
		payResultDto.setTime_expire(DateUtil.formatDate(expireTime, "yyyyMMddHHmmss"));
		int tradeAmount = logicOrderVo.getTotalPayPrice().multiply(new BigDecimal(100)).intValue();
		payResultDto.setTotal_fee(tradeAmount);
		buildExtraParam(logicOrderVo.getLogicOrderId(), openId, payResultDto);
		return payResultDto;
	}

	private void buildExtraParam(String wxProductId, String openId, WeiXinOrderDto payResultDto)
	{
		if (StringUtils.isNotBlank(openId))
		{
			payResultDto.setTrade_type("JSAPI");
			payResultDto.setOpenid(openId);
			return;
		}
		if (StringUtils.isNotBlank(wxProductId))
		{
			payResultDto.setTrade_type("NATIVE");
			payResultDto.setProduct_id(wxProductId);
		}
	}

	protected AliPayOrderDto buildAliPayDto(LogicOrderVo logicOrderVo, List<OrderVo> orderList)
	{
		AliPayOrderDto payResultDto = new AliPayOrderDto();
		payResultDto.setExter_invoke_ip(logicOrderVo.getOrderIp());
		payResultDto.setOut_trade_no(logicOrderVo.getLogicOrderId());
		String notfiyUrl = AlipayConfigBean.getPayConfigValue(ConfigConstant.ALIPAY_NOTIFY_URL);
		payResultDto.setNotify_url(notfiyUrl);
		String returnUrl = AlipayConfigBean.getPayConfigValue(ConfigConstant.ALIPAY_RETURN_URL);
		payResultDto.setReturn_url(returnUrl);
		payResultDto.setSubject("游学营地活动");
		payResultDto.setTotal_fee(CommonUtil.formatBigDecimal(logicOrderVo.getTotalPayPrice()));
		payResultDto.setBody("订单支付");
		return payResultDto;
	}

	private PayService getPayService(Integer payType)
	{
		if (PayTypeEnum.ALIPAY.getValue() == payType)
			return aliPayService;
		return weiXinPayService;
	}

}
