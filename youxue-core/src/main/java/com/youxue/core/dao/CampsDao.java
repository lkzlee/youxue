package com.youxue.core.dao;

import java.util.List;
import java.util.Map;

import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.vo.CampsDetailKey;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.ShopCartCampsDetail;

public interface CampsDao
{
	int deleteByPrimaryKey(String campsId);

	int insert(CampsVo record);

	int insertSelective(CampsVo record);

	CampsVo selectByPrimaryKey(String campsId);//TODO

	int updateByPrimaryKeySelective(CampsVo record);

	int updateByPrimaryKey(CampsVo record);

	Page<CampsVo> selectByConditions(Map<String, Object> queryConditions, int pageNo, int pageSize);

	List<CampsVo> getCampusListByType(CategoryTypeEnum type, int pageNo, int pageSize);

	List<ShopCartCampsDetail> selectShopCartCampsListByIds(List<CampsDetailKey> keyList);//TODO

	List<CampsVo> getHotCampusList(boolean ifCheckValid);

	List<CampsVo> getPriceCampusList(boolean ifCheckValid);
}