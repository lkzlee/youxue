package com.youxue.pc.order.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.exceptions.BusinessException;
import com.lkzlee.pay.utils.CommonUtil;
import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CampsDetailDao;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.dao.LogicOrderDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.service.coupon.CouponService;
import com.youxue.core.service.order.dto.AddOrderPersonDto;
import com.youxue.core.service.order.dto.AddTradeItemDto;
import com.youxue.core.service.order.dto.AddTradeOrderDto;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsDetailVo;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CouponCodeVo;
import com.youxue.core.vo.LogicOrderVo;
import com.youxue.pc.order.service.AddOrderPayService;

/**
 * 订单相关处理
 * @author lkzlee
 *
 */
@Controller
public class OrderController extends BaseController
{
	protected final static Log log = LogFactory.getLog(OrderController.class);
	@Resource(name = "pcAddOrderPayService")
	private AddOrderPayService pcAddOrderPayService;
	@Resource
	private CouponCodeDao couponCodeDao;
	@Resource
	private CouponService couponService;
	@Resource
	private CampsDetailDao campsDetailDao;
	@Resource
	private CampsDao campsDao;
	@Autowired
	JedisProxy jedisProxy;
	@Resource
	private LogicOrderDao logicOrderDao;

	/***
	 * 下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/pay/addTradeOrder.do", method = RequestMethod.POST)
	@ResponseBody
	public String addTrade(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AddTradeOrderDto orderData)
	{
		try
		{
			String ip = getCurrentLoginUserIp(request);
			String accountId = getCurrentLoginUserName(request);
			checkIfParamValidAndFillBaseInfo(orderData, accountId);
			BaseResponseDto responseDto = pcAddOrderPayService.addTradeOrderService(orderData, ip, accountId);
			return JsonUtil.serialize(responseDto);
		}
		catch (BusinessException e)
		{
			log.error("下单参数校验及流程处理，orderData=" + orderData + ",msg:" + e.getMessage());
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc(e.getMessage()));
		}
		catch (Exception e)
		{
			log.error("下单处理流程异常，orderData=" + orderData + ",msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后！"));
		}
	}

	/***
	 * 下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/pay/addTradeOrderById.do")
	@ResponseBody
	public String addTrade(HttpServletRequest request, HttpServletResponse response, String logicOrderId)
	{
		try
		{
			String ip = getCurrentLoginUserIp(request);
			String accountId = getCurrentLoginUserName(request);
			if (StringUtils.isBlank(accountId))
				throw new BusinessException("用户未登录，请检查");
			LogicOrderVo logicOrderVo = logicOrderDao.selectByPrimaryKey(logicOrderId, false);
			if (PayTypeEnum.WEIXIN_JS_API.getValue() == logicOrderVo.getPayType())
			{
				throw new BusinessException("请在微信端内完成支付");
			}
			BaseResponseDto responseDto = pcAddOrderPayService.addTradeOrderServiceById(logicOrderId, ip, accountId,
					null);
			return JsonUtil.serialize(responseDto);
		}
		catch (BusinessException e)
		{
			log.error("下单参数校验及流程处理，logicOrderId=" + logicOrderId + ",msg:" + e.getMessage());
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc(e.getMessage()));
		}
		catch (Exception e)
		{
			log.error("下单处理流程异常，logicOrderId=" + logicOrderId + ",msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后！"));
		}
	}

	/***
	 * 下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/pay/wxpay.do", method = RequestMethod.GET)
	public String wxPayPage(HttpServletRequest request, HttpServletResponse response, String logicOrderId,
			ModelMap modelMap)
	{
		try
		{

			String accountId = getCurrentLoginUserName(request);
			log.info("@@微信支付页面，logicOrderId=" + logicOrderId + ",accountI=" + accountId);
			if (StringUtils.isBlank(accountId))
			{
				modelMap.put("result", -2);
				modelMap.put("resultDesc", "用户未登录，请检查");
				return "/wxpay";
			}
			String payUrl = (String) jedisProxy.get(RedisConstant.getAddUserOrderKey(accountId, logicOrderId));
			if (StringUtils.isBlank(payUrl))
			{
				modelMap.put("result", -3);
				modelMap.put("resultDesc", "您请求的链接不存在，请检查");
				return "/wxpay";
			}
			modelMap.put("result", 100);
			modelMap.put("resultDesc", "操作成功");
			modelMap.put("payUrl", payUrl);
			LogicOrderVo logicOrderVo = logicOrderDao.selectByPrimaryKey(logicOrderId, false);
			modelMap.put("logicOrderId", logicOrderId);
			modelMap.put("tradeAmount", logicOrderVo.getTotalPayPrice());
		}
		catch (Exception e)
		{
			log.error("下单处理流程异常，logicOrderId=" + logicOrderId + ",msg:" + e.getMessage(), e);
			modelMap.put("result", -1);
			modelMap.put("resultDesc", "系统繁忙，请稍后！");
		}
		return "/wxpay";
	}

	/***
	 * 下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/pay/query.do")
	@ResponseBody
	public String wxPayQuery(HttpServletRequest request, HttpServletResponse response, String logicOrderId)
	{
		try
		{

			String accountId = getCurrentLoginUserName(request);
			log.info("@@微信支付页面，logicOrderId=" + logicOrderId + ",accountId=" + accountId);

			LogicOrderVo logicOrderVo = logicOrderDao.selectByPrimaryKey(logicOrderId, false);
			if (LogicOrderVo.UNPAY != logicOrderVo.getPayStatus())
			{
				return JsonUtil.serialize(BaseResponseDto.successDto().setDesc("支付成功"));
			}
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("未支付,继续轮询"));
		}
		catch (Exception e)
		{
			log.error("下单处理流程异常，logicOrderId=" + logicOrderId + ",msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统异常，请检查"));
		}
	}

	public void checkIfParamValidAndFillBaseInfo(AddTradeOrderDto orderData, String accountId)
	{
		if (StringUtils.isBlank(accountId))
			throw new BusinessException("用户未登录，请检查");
		if (orderData == null || orderData.getPayType() == null)
			throw new BusinessException("参数非法");
		int pType = Integer.parseInt(orderData.getPayType().trim());
		PayTypeEnum payType = PayTypeEnum.getByValue(pType);
		if (payType != PayTypeEnum.ALIPAY && payType != PayTypeEnum.WEIXIN_PAY)
			throw new BusinessException("非法支付方式");
		if (ArrayUtils.isEmpty(orderData.getOrderList()))
			throw new BusinessException("无任何订单信息");
		/***
		 * 校验下单的人数和 出行人人数，校验手机号、email合法性，校验支付方式，校验优惠券合法性
		 */
		boolean isUsed = false;
		String codeId = null;
		AddTradeItemDto orderItemList[] = orderData.getOrderList();
		for (AddTradeItemDto ote : orderItemList)
		{
			int totalPerson = Integer.parseInt(ote.getTotalCount().trim());
			if (totalPerson <= 0)
				throw new BusinessException("对应订单提交的出行人数非法，请检查");
			AddOrderPersonDto personList[] = ote.getOutPersonList();
			if (ArrayUtils.isEmpty(personList))
				throw new BusinessException("对应的订单没有出现人信息，请检查");
			if (totalPerson != personList.length)
				throw new BusinessException("对应订单提交的人数和出行人数不符");

			if (CommonUtil.isValidEmail(ote.getContactPhone()))
			{
				throw new BusinessException("订单对应联系人的电话有误，请检查");
			}
			CampsVo camps = campsDao.selectByPrimaryKey(ote.getCampsId());
			CampsDetailVo detailVo = campsDetailDao.selectByPrimaryKey(ote.getDetailId());
			if (camps == null || camps.getStatus() != CampsVo.NORMAL)
			{
				throw new BusinessException("下单有误，对应的营地不存在或者营地未开放");
			}
			if (camps.getDeadlineDate().before(new Date()))
			{
				throw new BusinessException("营地报名截止时间已过,截止时间:"
						+ DateUtil.formatDate(camps.getDeadlineDate(), "yyyy-MM-dd"));
			}
			if (detailVo == null)
			{
				throw new BusinessException("下单有误，对应的营地详情缺失，请联系相关客服");
			}
			if (detailVo.getDetailStartTime().before(new Date()))
			{
				throw new BusinessException("营地已经开始,开始时间:"
						+ DateUtil.formatDate(detailVo.getDetailStartTime(), "yyyy-MM-dd"));
			}
			BigDecimal couponPrice = BigDecimal.ZERO;
			BigDecimal totalPrice = detailVo.getDetailPrice().multiply(new BigDecimal(totalPerson));
			codeId = ote.getCodeId();
			if (!StringUtils.isBlank(ote.getCodeId()))
			{
				try
				{
					if (couponService.isUseableForCamps(ote.getCodeId(), ote.getCampsId()))
					{
						CouponCodeVo coupon = couponCodeDao.selectCouponByCode(ote.getCodeId(), false);
						if (coupon == null || coupon.getStatus() != CouponCodeVo.NORMAL)
						{
							throw new BusinessException("优惠券使用有误，对应的优惠券不存在");
						}
						couponPrice = coupon.getCodeAmount().multiply(new BigDecimal(totalPerson));
						isUsed = true;
					}
				}
				catch (Exception e)
				{
					couponPrice = BigDecimal.ZERO;
				}
			}
			BigDecimal totalPayPrice = totalPrice.subtract(couponPrice);
			if (BigDecimal.ZERO.compareTo(totalPayPrice) > 0)
				throw new BusinessException("提交的订单优惠券大于支付金额，有误，请检查");
			ote.setPayPrice(totalPayPrice);
			ote.setTotalPrice(totalPrice);
			ote.setCodePrice(couponPrice);
		}
		if (!StringUtils.isBlank(codeId) && !isUsed)
		{
			throw new BusinessException("下单有误，该优惠券不适用于该营地，请检查");
		}
	}
}
