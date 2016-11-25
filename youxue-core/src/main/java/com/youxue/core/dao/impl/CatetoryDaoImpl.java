package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CategoryVo;

@Repository
public class CatetoryDaoImpl extends BaseDao implements CatetoryDao
{

	@Override
	public int deleteByPrimaryKey(String categoryId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.CategoryDao.deleteByPrimaryKey", categoryId);
	}

	@Override
	public int insert(CategoryVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.CategoryDao.insert", record);
	}

	@Override
	public int insertSelective(CategoryVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.CategoryDao.insertSelective", record);
	}

	@Override
	public CategoryVo selectByPrimaryKey(String categoryId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.CategoryDao.selectByPrimaryKey", categoryId);
	}

	@Override
	public int updateByPrimaryKeySelective(CategoryVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.CategoryDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(CategoryVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.CategoryDao.updateByPrimaryKey", record);
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
		List<String> result = sqlSessionTemplate.selectList("com.youxue.core.dao.CategoryDao.getCampsIdByType",
				conditions);
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.selectByCampsIds", result);
	}

	@Override
	public List<CategoryVo> selectByCategoryType(Integer categoryType)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CategoryDao.selectByCategoryType", categoryType);
	}

}
