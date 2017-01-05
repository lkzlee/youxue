package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.CampsDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.Page;

@Repository
public class CampsDaoImpl extends BaseDao implements CampsDao
{

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
		return getPageList(page, "com.youxue.core.dao.CampsDao.selectByConditions",
				"com.youxue.core.dao.CampsDao.selectCountByConditions", queryConditions, sqlSessionTemplate);
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
	public List<CampsVo> selectCampsListByIds(List<String> campsId)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.selectCampsListByIds", campsId);
	}

	@Override
	public List<CampsVo> getHotCampusList(boolean ifCheckValid)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.getHotCampusList", ifCheckValid);
	}

	@Override
	public List<CampsVo> getPriceCampusList(boolean ifCheckValid)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.getPriceCampusList", ifCheckValid);
	}
}
