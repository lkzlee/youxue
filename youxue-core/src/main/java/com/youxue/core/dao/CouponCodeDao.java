package com.youxue.core.dao;

import java.util.Map;

import com.youxue.core.vo.CouponCodeVo;
import com.youxue.core.vo.Page;

public interface CouponCodeDao
{
	int deleteByPrimaryKey(String codeId);

	int insert(CouponCodeVo record);

	int insertSelective(CouponCodeVo record);

	CouponCodeVo selectByPrimaryKey(String codeId);

	int updateByPrimaryKeySelective(CouponCodeVo record);

	int updateByPrimaryKey(CouponCodeVo record);

	CouponCodeVo selectCouponByCode(String codeId, boolean lock);

	Page<CouponCodeVo> selectPageByConditions(Page<CouponCodeVo> page, Map<String, Object> conditions);
}