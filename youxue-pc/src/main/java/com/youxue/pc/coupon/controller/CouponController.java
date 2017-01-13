package com.youxue.pc.coupon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.exceptions.BusinessException;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CouponCodeVo;
import com.youxue.pc.order.controller.OrderController;

@Controller
public class CouponController extends BaseController
{
	protected final static Log log = LogFactory.getLog(OrderController.class);
	@Resource
	private CouponCodeDao couponCodeDao;

	/***
	 * 下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/coupon/getCouponByCode.do")
	@ResponseBody
	public String getCouponCode(HttpServletRequest request, HttpServletResponse response, String codeId)
	{
		try
		{
			String accountId = getCurrentLoginUserName(request);
			if (StringUtils.isEmpty(accountId))
				return JsonUtil.serialize(BaseResponseDto.notLoginDto());
			if (StringUtils.isBlank(codeId))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			}
			CouponCodeVo coupon = couponCodeDao.selectCouponByCode(codeId, false);
			if (coupon == null || coupon.getStatus() != CouponCodeVo.NORMAL)
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			coupon.setResult(100);
			coupon.setResultDesc("查询成功");
			return JsonUtil.serialize(coupon);
		}
		catch (BusinessException e)
		{
			log.error("获取优惠券错误，codeId=" + codeId + ",msg:" + e.getMessage());
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc(e.getMessage()));
		}
		catch (Exception e)
		{
			log.error("获取优惠券流程异常，codeId=" + codeId + ",msg:" + e.getMessage(), e);
			return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("系统繁忙，请稍后！"));
		}
	}
}
