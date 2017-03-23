package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.dao.CampsDetailDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.vo.CampsDetailKey;
import com.youxue.core.vo.CampsDetailVo;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.ShopCartCampsDetail;

@Repository
public class CampsDaoImpl extends BaseDao implements CampsDao
{

	@Autowired
	CampsDetailDao campsDetailDao;

	@Override
	public int deleteByPrimaryKey(String campsId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.CampsDao.deleteByPrimaryKey", campsId);
	}

	@Override
	public int insert(CampsVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.CampsDao.insert", record);
	}

	@Override
	public int insertSelective(CampsVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.CampsDao.insertSelective", record);
	}

	@Override
	public CampsVo selectByPrimaryKey(String campsId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.CampsDao.selectByPrimaryKey", campsId);
	}

	@Override
	public int updateByPrimaryKeySelective(CampsVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.CampsDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(CampsVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.CampsDao.updateByPrimaryKey", record);
	}

	@Override
	public Page<CampsVo> selectByConditions(Map<String, Object> queryConditions, int pageNo, int pageSize)
	{
		Page<CampsVo> page = new Page<CampsVo>(pageNo, pageSize);
		Page<CampsVo> campsList = getPageList(page, "com.youxue.core.dao.CampsDao.selectByConditions",
				"com.youxue.core.dao.CampsDao.selectCountByConditions", queryConditions, sqlSessionTemplate);
		if (CollectionUtils.isEmpty(campsList.getResultList()))
		{
			return campsList;
		}
		for (CampsVo camps : campsList.getResultList())
		{
			fillCampsDetails(camps);
		}
		return campsList;
	}

	@Override
	public List<CampsVo> getCampusListByType(CategoryTypeEnum type, int pageNo, int pageSize)
	{
		int skipResults = (pageNo - 1) * pageSize;
		if (pageNo < 1)
		{
			skipResults = 0;
		}
		int maxResults = pageSize;
		Map<String, Integer> conditions = new HashMap<>();
		conditions.put("categoryType", type.getValue());
		conditions.put("startIndex", skipResults);
		conditions.put("size", maxResults);
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.getCampusListByType", conditions);
	}

	@Override
	public List<ShopCartCampsDetail> selectShopCartCampsListByIds(List<CampsDetailKey> keyList)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.selectShopCartCampsListByIds", keyList);
	}

	@Override
	public List<CampsVo> getHotCampusList(boolean ifCheckValid)
	{
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("ifCheckValid", ifCheckValid);
		List<CampsVo> campsList = sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.getHotCampusList",
				conditions);
		if (CollectionUtils.isEmpty(campsList))
		{
			return campsList;
		}
		for (CampsVo camps : campsList)
		{
			fillCampsDetails(camps);
		}
		return campsList;
	}

	@Override
	public List<CampsVo> getPriceCampusList(boolean ifCheckValid)
	{
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("ifCheckValid", ifCheckValid);
		List<CampsVo> campsList = sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.getPriceCampusList",
				conditions);
		if (CollectionUtils.isEmpty(campsList))
		{
			return campsList;
		}
		for (CampsVo camps : campsList)
		{
			fillCampsDetails(camps);
		}
		return campsList;
	}

	private void fillCampsDetails(CampsVo camps)
	{
		CampsDetailVo detail = campsDetailDao.selectCheapestByCampsId(camps.getCampsId());
		if (detail != null)
		{
			camps.setTotalPrice(detail.getDetailPrice());
			camps.setStartDate(detail.getDetailStartTime());
			camps.setDurationTime(detail.getDuration());
			camps.setDetailName(detail.getDetailName());
		}
	}
}
