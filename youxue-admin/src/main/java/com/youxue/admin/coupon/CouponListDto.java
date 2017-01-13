package com.youxue.admin.coupon;

import java.io.Serializable;
import java.util.List;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.vo.CouponCodeVo;

public class CouponListDto extends BaseResponseDto implements Serializable
{
	private List<CouponCodeVo> couponList;

	public List<CouponCodeVo> getCouponList()
	{
		return couponList;
	}

	public void setCouponList(List<CouponCodeVo> couponList)
	{
		this.couponList = couponList;
	}

}
