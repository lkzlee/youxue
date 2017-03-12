package com.youxue.core.service.coupon.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.lkzlee.pay.exceptions.BusinessException;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.service.coupon.CouponService;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CouponCodeVo;

@Service("couponService")
public class CouponServiceImpl implements CouponService
{
	protected final static Log LOG = LogFactory.getLog(CouponServiceImpl.class);
	@Resource
	private CouponCodeDao couponCodeDao;
	@Resource
	private CampsDao campsDao;

	@Override
	public boolean isUseableForCamps(String codeId, String campsId)
	{
		CampsVo campsVo = campsDao.selectByPrimaryKey(campsId);
		CouponCodeVo coupon = couponCodeDao.selectCouponByCode(codeId, false);
		if (coupon == null || coupon.getStatus() != CouponCodeVo.NORMAL)
		{
			throw new BusinessException("优惠券使用有误，对应的优惠券不存在");
		}
		LOG.info("coupon categoryIds:" + coupon.getCategoryIds());
		if (StringUtils.isNotBlank(coupon.getCategoryIds())
				&& coupon.getCategoryIds().contains(campsVo.getCampsSubjectId())
				|| StringUtils.isNotBlank(coupon.getCategoryIds())
				&& coupon.getCategoryIds().contains(campsVo.getCampsLocaleId()))
		{
			return true;
		}
		return false;
	}

}
