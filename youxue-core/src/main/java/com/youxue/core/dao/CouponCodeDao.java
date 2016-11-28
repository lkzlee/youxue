package com.youxue.core.dao;

import com.youxue.core.vo.CouponCodeVo;

public interface CouponCodeDao
{
	int deleteByPrimaryKey(String codeId);

	int insert(CouponCodeVo record);

	int insertSelective(CouponCodeVo record);

	CouponCodeVo selectByPrimaryKey(String codeId);

	int updateByPrimaryKeySelective(CouponCodeVo record);

	int updateByPrimaryKey(CouponCodeVo record);

	CouponCodeVo selectCouponByCode(String codeId, boolean lock);
}