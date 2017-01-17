package com.youxue.core.dao;

import com.youxue.core.vo.AboutUsVo;

public interface AboutUsDao
{
	int deleteByPrimaryKey(String type);

	int insert(AboutUsVo record);

	int insertSelective(AboutUsVo record);

	AboutUsVo selectByPrimaryKey(String type);

	int updateByPrimaryKeySelective(AboutUsVo record);

	int updateByPrimaryKey(AboutUsVo record);
}