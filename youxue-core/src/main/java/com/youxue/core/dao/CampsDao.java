package com.youxue.core.dao;

import java.util.Map;

import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.Page;

public interface CampsDao
{
	int deleteByPrimaryKey(String campsId);

	int insert(CampsVo record);

	int insertSelective(CampsVo record);

	CampsVo selectByPrimaryKey(String campsId);

	int updateByPrimaryKeySelective(CampsVo record);

	int updateByPrimaryKey(CampsVo record);

	Page<CampsVo> selectByConditions(Map<String, Object> queryConditions, int pageNo, int pageSize);
}