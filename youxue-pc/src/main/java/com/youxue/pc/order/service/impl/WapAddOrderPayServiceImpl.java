package com.youxue.pc.order.service.impl;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lkzlee.pay.bean.WeiXinConfigBean;
import com.lkzlee.pay.constant.ConfigConstant;
import com.lkzlee.pay.enums.SignTypeEnum;
import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.third.dto.AbstThirdPayDto;
import com.lkzlee.pay.third.weixin.dto.response.WeiXinOrderResultDto;
import com.lkzlee.pay.utils.RandomUtil;
import com.lkzlee.pay.utils.TreeMapUtil;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.CommonConstant;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.service.order.dto.AddTradeResonseDto;
import com.youxue.core.service.order.dto.WxJsPayParamDto;
import com.youxue.core.util.PropertyUtils;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.core.vo.OrderVo;

@Service("wapAddOrderPayService")
public class WapAddOrderPayServiceImpl extends AbstAddOrderPayService
{

	@Override
	protected BaseResponseDto parseOrderParam(Object param, String accountId, String logicOrderId)
	{
		if (param == null)
			throw new BusinessException("param为空，下单错误");
		AddTradeResonseDto responseDto = new AddTradeResonseDto();
		if (param instanceof WeiXinOrderResultDto)
		{
			WeiXinOrderResultDto resultDto = (WeiXinOrderResultDto) param;
			// 参数
			String prePayId = resultDto.getPrepay_id();
			WxJsPayParamDto wxPayParam = buidlWxJsPayParam(prePayId);
			responseDto.setWxPayParam(wxPayParam);
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

	private WxJsPayParamDto buidlWxJsPayParam(String prePayId)
	{
		String timeStamp = System.currentTimeMillis() + "";
		String appId = WeiXinConfigBean.getPayConfigValue(ConfigConstant.WEIXIN_APP_ID);
		String nonce_str = RandomUtil.generateCode();

		TreeMap<String, String> signMap = TreeMapUtil.getInitTreeMapAsc();
		signMap.put("timeStamp", timeStamp);
		signMap.put("appId", appId);
		signMap.put("package", "prepay_id=" + prePayId);
		signMap.put("nonceStr", nonce_str);
		signMap.put("signType", "MD5");
		String source = TreeMapUtil.getTreeMapString(signMap) + "&key="
				+ WeiXinConfigBean.getPayConfigValue(ConfigConstant.WEIXIN_PAY_SIGN_KEY);
		String sign = SignTypeEnum.MD5.sign(source, null);

		WxJsPayParamDto wxPayParam = new WxJsPayParamDto();
		wxPayParam.setTimeStamp(timeStamp);
		wxPayParam.setNonceStr(nonce_str);
		wxPayParam.setSignType("MD5");
		wxPayParam.setPaySign(sign);
		wxPayParam.setPackage_("prepay_id=" + prePayId);
		wxPayParam.setAppId(appId);
		return wxPayParam;
	}

	@Override
	protected AbstThirdPayDto buildThirdPayOrderByLogicOrderId(String logicOrderId, boolean isDel, String openId)
	{
		LogicOrderVo logicOrderVo = logicOrderDao.selectByPrimaryKey(logicOrderId, false);
		List<OrderVo> orderList = orderDao.selectOrderByLogicOrderId(logicOrderId, false);
		if (logicOrderVo == null)
			throw new BusinessException("查询订单不存在，请检查");
		PayTypeEnum payTypeEnum = PayTypeEnum.getByValue(logicOrderVo.getPayType());
		AbstThirdPayDto thirdPayDto = null;
		switch (payTypeEnum)
		{
			case WEIXIN_JS_API:
			{
				thirdPayDto = buildWeiXinPayDto(logicOrderVo, orderList, openId);
				break;
			}
			default:
			{
				LOG.fatal("支付方式错误，请检查，payTypeEnum=" + payTypeEnum);
				throw new BusinessException("支付方式错误，请检查，payTypeEnum=" + payTypeEnum + ",logicOrderId=" + logicOrderId);
			}
		}
		if (isDel)
		{
			try
			{
				List<String> campsIdList = orderList.stream().map(t -> {
					return t.getCampsId();
				}).collect(Collectors.toList());
				String[] ids = campsIdList.toArray(new String[0]);
				jedisProxy.hdel(RedisConstant.SHOP_CART_KEY + logicOrderVo.getAccountId(), ids);
			}
			catch (Exception e)
			{
				LOG.fatal("删除用户购物车列表，异常", e);
			}
		}
		return thirdPayDto;
	}

	@Override
	protected BaseResponseDto paramContinuePayPara(String accountId, String logicOrderId)
	{
		AddTradeResonseDto responseDto = new AddTradeResonseDto();
		String payUrl = (String) jedisProxy.get(RedisConstant.getAddUserOrderKey(accountId, logicOrderId));
		if (StringUtils.isEmpty(payUrl))
		{
			responseDto.setResult(-3);
			responseDto.setResultDesc("你需要支付的订单已过期，请重新支付");
			return responseDto;
		}
		String wxOrderUrl = PropertyUtils
				.getProperty(CommonConstant.WEI_XIN_ORDER_URL, "http://127.0.0.1/pay/wxpay.do");
		responseDto.setPayUrl(wxOrderUrl + "?logicOrderId=" + logicOrderId);
		responseDto.setResult(100);
		responseDto.setResultDesc("下单成功");
		return responseDto;
	}

}
