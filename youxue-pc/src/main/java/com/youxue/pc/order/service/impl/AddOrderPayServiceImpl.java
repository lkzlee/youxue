package com.youxue.pc.order.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkzlee.pay.bean.AlipayConfigBean;
import com.lkzlee.pay.bean.WeiXinConfigBean;
import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.service.PayService;
import com.lkzlee.pay.third.alipay.dto.request.AliPayOrderDto;
import com.lkzlee.pay.third.dto.AbstThirdPayDto;
import com.lkzlee.pay.third.weixin.dto.request.WeiXinOrderDto;
import com.lkzlee.pay.third.weixin.dto.response.WeiXinOrderResultDto;
import com.lkzlee.pay.utils.CommonUtil;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.service.order.OrderService;
import com.youxue.core.service.order.dto.AddTradeOrderDto;
import com.youxue.core.service.order.dto.AddTradeResonseDto;
import com.youxue.core.util.PropertyUtils;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.OrderVo;
import com.youxue.pc.order.service.AddOrderPayService;

@Service("addOrderPayService")
public class AddOrderPayServiceImpl implements AddOrderPayService
{
	protected final static Log LOG = LogFactory.getLog(AddOrderPayServiceImpl.class);
	@Resource(name = "weiXinOrderPayService")
	private PayService weiXinPayService;
	@Resource(name = "aliPayOrderPayService")
	private PayService aliPayService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderDao orderDao;
	@Resource
	private LogicOrderDao logicOrderDao;
	@Resource
	private CampsDao campsDao;
	@Autowired
	JedisProxy jedisProxy;

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

			PayService payService = getPayService(orderData.getPayType());
			AbstThirdPayDto thirdPayDto = buildThirdPayOrderByLogicOrderId(logicOrderId);
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
		catch (Exception e)
		{
			LOG.fatal("系统异常，请检查，msg：" + e.getMessage(), e);
			return BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后");
		}
	}

	private BaseResponseDto parseOrderParam(Object param, String accountId, String logicOrderId)
	{
		if (param == null)
			throw new BusinessException("param为空，下单错误");
		AddTradeResonseDto responseDto = new AddTradeResonseDto();
		if (param instanceof String)
		{
			String payUrl = (String) param;
			responseDto.setPayUrl(payUrl);//支付宝直接跳转该连接进行支付
		}
		else if (param instanceof WeiXinOrderResultDto)
		{
			WeiXinOrderResultDto resultDto = (WeiXinOrderResultDto) param;
			/**
			 * 微信需要自己构造支付页面因此保存支付链接
			 */
			jedisProxy.setex(RedisConstant.getAddUserOrderKey(accountId, logicOrderId), resultDto.getCode_url(), 7200);
			String wxOrderUrl = PropertyUtils.getProperty(CommonConstant.WEI_XIN_ORDER_URL,
					"http://127.0.0.1/pay/wxpay.do");
			responseDto.setPayUrl(wxOrderUrl + "?logicOrderId=" + logicOrderId);
		}
		else
		{
			LOG.fatal("下单返回类型错误，请检查，param=" + param);
			throw new BusinessException("param类型错误，下单错误");
		}
		responseDto.setResult(100);
		responseDto.setResultDesc("下单成功");
		return responseDto;
	}

	private AbstThirdPayDto buildThirdPayOrderByLogicOrderId(String logicOrderId)
	{
		LogicOrderVo logicOrderVo = logicOrderDao.selectByPrimaryKey(logicOrderId, false);
		List<OrderVo> orderList = orderDao.selectOrderByLogicOrderId(logicOrderId, false);
		if (logicOrderVo == null)
			throw new BusinessException("查询订单不存在，请检查");
		PayTypeEnum payTypeEnum = PayTypeEnum.getByValue(logicOrderVo.getPayType());
		AbstThirdPayDto thirdPayDto = null;
		switch (payTypeEnum)
		{
			case ALIPAY:
			{
				thirdPayDto = buildAliPayDto(logicOrderVo, orderList);
				break;
			}
			case WEIXIN_APY:
			{
				thirdPayDto = buildWeiXinPayDto(logicOrderVo, orderList);
				break;
			}
			default:
			{
				LOG.fatal("支付方式错误，请检查，payTypeEnum=" + payTypeEnum);
				throw new BusinessException("支付方式错误，请检查，payTypeEnum=" + payTypeEnum + ",logicOrderId=" + logicOrderId);
			}
		}
		return thirdPayDto;
	}

	private WeiXinOrderDto buildWeiXinPayDto(LogicOrderVo logicOrderVo, List<OrderVo> orderList)
	{
		WeiXinOrderDto payResultDto = new WeiXinOrderDto();
		//		payResultDto.setAttach(attach);
		payResultDto.setBody("游学营地活动");
		//		payResultDto.setDetail(detail);
		payResultDto.setFee_type("CNY");
		//		payResultDto.setGoods_tag(goods_tag);
		//		payResultDto.setLimit_pay(limit_pay);
		String notfiyUrl = WeiXinConfigBean.getPayConfigValue(CommonConstant.WEIXIN_PAY_NOTIFY_URL);
		payResultDto.setNotify_url(notfiyUrl);
		//		payResultDto.setOpenid(openid);
		payResultDto.setOut_trade_no(logicOrderVo.getLogicOrderId());

		payResultDto.setSpbill_create_ip(logicOrderVo.getOrderIp());

		Date expireTime = DateUtil.getIntervalSeconds(logicOrderVo.getCreateTime(), 7200);
		payResultDto.setTime_start(DateUtil.formatDate(logicOrderVo.getCreateTime(), "yyyyMMddHHmmss"));
		payResultDto.setTime_expire(DateUtil.formatDate(expireTime, "yyyyMMddHHmmss"));
		int tradeAmount = logicOrderVo.getTotalPayPrice().multiply(new BigDecimal(100)).intValue();
		payResultDto.setTotal_fee(tradeAmount);
		payResultDto.setTrade_type("NATIVE");
		String subOrderId = "";
		for (OrderVo order : orderList)
		{
			subOrderId += "|" + order.getOrderId();
		}
		payResultDto.setProduct_id(subOrderId.substring(1));
		return payResultDto;
	}

	private AliPayOrderDto buildAliPayDto(LogicOrderVo logicOrderVo, List<OrderVo> orderList)
	{
		AliPayOrderDto payResultDto = new AliPayOrderDto();
		payResultDto.setExter_invoke_ip(logicOrderVo.getOrderIp());
		payResultDto.setOut_trade_no(logicOrderVo.getLogicOrderId());
		String notfiyUrl = AlipayConfigBean.getPayConfigValue(CommonConstant.ALIPAY_NOTIFY_URL);
		payResultDto.setNotify_url(notfiyUrl);
		String returnUrl = AlipayConfigBean.getPayConfigValue(CommonConstant.ALIPAY_RETURN_URL);
		payResultDto.setReturn_url(returnUrl);
		payResultDto.setSubject("游学营地活动");
		payResultDto.setTotal_fee(CommonUtil.formatBigDecimal(logicOrderVo.getTotalPayPrice()));
		//		payResultDto.setBody();
		return payResultDto;
	}

	private PayService getPayService(Integer payType)
	{
		if (PayTypeEnum.ALIPAY.getValue() == payType)
			return aliPayService;
		return weiXinPayService;
	}

}
