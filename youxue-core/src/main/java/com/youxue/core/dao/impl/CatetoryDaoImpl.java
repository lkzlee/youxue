package com.youxue.core.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.CatetoryDao;
import com.youxue.core.enums.CategoryTypeEnum;
import com.youxue.core.vo.CampsVo;
import com.youxue.core.vo.CategoryVo;
import com.youxue.core.vo.Page;

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
		Page<String> page = new Page<String>(pageNo, pageSize);
		Page<String> result = getPageList(page, "com.youxue.core.dao.CategoryDao.getCampsIdByType",
				"com.youxue.core.dao.CategoryDao.getCampsCountByType", type.getValue(), sqlSessionTemplate);
		if (CollectionUtils.isEmpty(result.getResult()))
		{
			return Collections.emptyList();
		}

		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDao.selectByCampsIds", result.getResult());
	}

	@Override
	public List<CategoryVo> selectByCategoryType(Integer categoryType)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CategoryDao.selectByCategoryType", categoryType);
	}

}
