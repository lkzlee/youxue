package com.youxue.core.dao;

import java.util.List;

import com.youxue.core.vo.RefundVo;

public interface RefundDao
{
	int deleteByPrimaryKey(String orderId);

	int insert(RefundVo record);

	int insertSelective(RefundVo record);

	RefundVo selectByPrimaryKey(String orderId);

	int updateByPrimaryKeySelective(RefundVo record);

	int updateByPrimaryKey(RefundVo record);

	List<RefundVo> selectInitRefundByPay(int value);
}