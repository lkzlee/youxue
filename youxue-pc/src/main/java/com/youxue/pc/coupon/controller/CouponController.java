package com.youxue.pc.coupon.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkzlee.pay.exceptions.BusinessException;
import com.youxue.core.common.BaseController;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.service.coupon.CouponService;
import com.youxue.core.util.JsonUtil;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CouponCodeVo;
import com.youxue.pc.coupon.dto.CalcPayAmountDto;
import com.youxue.pc.order.controller.OrderController;

@Controller
public class CouponController extends BaseController
{
	protected final static Log log = LogFactory.getLog(OrderController.class);
	@Resource
	private CouponCodeDao couponCodeDao;
	@Resource
	private CouponService couponService;
	@Resource
	private CampsDao campsDao;

	/***
	 * 下单接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/coupon/getCouponByCode.do")
	@ResponseBody
	public String getCouponCode(HttpServletRequest request, HttpServletResponse response, String codeId,
			String campsIds, String totalPersons)
	{
		try
		{
			String accountId = getCurrentLoginUserName(request);
			if (StringUtils.isEmpty(accountId))
				return JsonUtil.serialize(BaseResponseDto.notLoginDto());

			if (StringUtils.isBlank(campsIds) || StringUtils.isBlank(totalPersons))
			{
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			}
			String[] campsIdArr = campsIds.split(",");
			String[] totalsArr = totalPersons.split(",");
			if (ArrayUtils.isEmpty(campsIdArr) || ArrayUtils.isEmpty(totalsArr))
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("参数非法"));
			if (campsIdArr.length != totalsArr.length)
				return JsonUtil.serialize(BaseResponseDto.errorDto().setDesc("选择的营地与出行人数不对应"));
			BigDecimal codeAmount = BigDecimal.ZERO;
			BigDecimal payAmount = BigDecimal.ZERO;
			if (StringUtils.isBlank(codeId))
			{
				for (int i = 0; i < campsIdArr.length; i++)
				{
					String campsId = campsIdArr[i];
					String totalPerson = totalsArr[i].trim();
					CampsVo campsVo = campsDao.selectByPrimaryKey(campsId);
					payAmount = payAmount.add(campsVo.getTotalPrice().multiply(new BigDecimal(totalPerson)));
				}
			}
			else
			{
				boolean isUsed = false;
				for (int i = 0; i < campsIdArr.length; i++)
				{
					String campsId = campsIdArr[i];
					String totalPerson = totalsArr[i].trim();
					CampsVo campsVo = campsDao.selectByPrimaryKey(campsId);
					BigDecimal perTotalAmount = campsVo.getTotalPrice().multiply(new BigDecimal(totalPerson));
					BigDecimal couponPrice = BigDecimal.ZERO;
					try
					{
						CouponCodeVo coupon = couponCodeDao.selectCouponByCode(codeId, false);
						if (coupon == null || coupon.getStatus() != CouponCodeVo.NORMAL)
						{
							throw new BusinessException("优惠券使用有误，对应的优惠券不存在");
						}
						LOG.info("coupon categoryIds:" + coupon.getCategoryIds());
						if (couponService.isUseableForCamps(codeId, campsId))
						{
							couponPrice = coupon.getCodeAmount().multiply(new BigDecimal(totalPerson));
							codeAmount = codeAmount.add(couponPrice);
							isUsed = true;
						}
					}
					catch (Exception e)
					{
						couponPrice = BigDecimal.ZERO;
					}
					payAmount = payAmount.add(perTotalAmount.subtract(couponPrice));
				}
			}
			CalcPayAmountDto calcDto = new CalcPayAmountDto();
			calcDto.setResult(100);
			calcDto.setResultDesc("查询成功");
			calcDto.setCodeAmount(codeAmount);
			calcDto.setPayAmount(payAmount);
			return JsonUtil.serialize(calcDto);
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
