package com.youxue.core.dao;

import com.youxue.core.vo.SurroundProductVo;

public interface SurroundProductDao
{
	int deleteByPrimaryKey(String productId);

	int insert(SurroundProductVo record);

	int insertSelective(SurroundProductVo record);

	SurroundProductVo selectByPrimaryKey(String productId);

	int updateByPrimaryKeySelective(SurroundProductVo record);

	int updateByPrimaryKey(SurroundProductVo record);

	SurroundProductVo selectProductByType(int type);
}