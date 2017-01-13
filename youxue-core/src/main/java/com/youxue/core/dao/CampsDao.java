package com.youxue.core.dao;

import java.util.List;
import java.util.Map;

import com.youxue.core.enums.CategoryTypeEnum;
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

	List<CampsVo> getCampusListByType(CategoryTypeEnum type, int pageNo, int pageSize);

	List<CampsVo> selectCampsListByIds(List<String> keySet);

	List<CampsVo> getHotCampusList(boolean ifCheckValid);

	List<CampsVo> getPriceCampusList(boolean ifCheckValid);
}