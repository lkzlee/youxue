package com.youxue.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.NewsVoDao;
import com.youxue.core.vo.NewsVo;
import com.youxue.core.vo.Page;

@Repository
public class NewsVoDaoImpl extends BaseDao implements NewsVoDao
{

	@Override
	public int deleteByPrimaryKey(String newsId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.NewsDao.deleteByPrimaryKey", newsId);
	}

	@Override
	public int insert(NewsVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.NewsDao.insert", record);
	}

	@Override
	public int insertSelective(NewsVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.NewsDao.insertSelective", record);
	}

	@Override
	public NewsVo selectByPrimaryKey(String newsId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.NewsDao.selectByPrimaryKey", newsId);
	}

	@Override
	public int updateByPrimaryKeySelective(NewsVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.NewsDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(NewsVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.NewsDao.updateByPrimaryKey", record);
	}

	@Override
	public Page<NewsVo> selectByPage(int pageNo, int pageSize)
	{
		Page<NewsVo> page = new Page<NewsVo>(pageNo, pageSize);
		return getPageList(page, "com.youxue.core.dao.NewsDao.selectByPage",
				"com.youxue.core.dao.CampsDao.selectCountByPage", Maps.newHashMap(), sqlSessionTemplate);
	}

}
