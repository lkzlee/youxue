package com.youxue.core.dao;

import java.util.List;

import com.youxue.core.vo.CampsDetailVo;

public interface CampsDetailDao
{

	int deleteByPrimaryKey(String detailId);

	int insert(CampsDetailVo record);

	int insertSelective(CampsDetailVo record);

	CampsDetailVo selectByPrimaryKey(String detailId);

	int updateByPrimaryKeySelective(CampsDetailVo record);

	int updateByPrimaryKey(CampsDetailVo record);

	List<CampsDetailVo> selectByCampsId(String campsId);

	/**
	 * @param campsId
	 * @return 获取价格最低的那一条营地信息
	 */
	CampsDetailVo selectCheapestByCampsId(String campsId);
}