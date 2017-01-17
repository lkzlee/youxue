package com.youxue.core.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.WordCountDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.WordCountVo;

@Repository
public class WordCountDaoImpl extends BaseDao implements WordCountDao
{

	@Override
	public int deleteByPrimaryKey(String word)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.WordCountDao.deleteByPrimaryKey", word);
	}

	@Override
	public int insert(WordCountVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.WordCountDao.insert", record);
	}

	@Override
	public int insertSelective(WordCountVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.WordCountDao.insertSelective", record);
	}

	@Override
	public WordCountVo selectByPrimaryKey(String word)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.WordCountDao.selectByPrimaryKey", word);
	}

	@Override
	public int updateByPrimaryKeySelective(WordCountVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.WordCountDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(WordCountVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.WordCountDao.updateByPrimaryKey", record);
	}

	@Override
	public Page<WordCountVo> selectPageByConditions(Page<WordCountVo> page, Map<String, Object> conditions)
	{
		return getPageList(page, "com.youxue.core.dao.WordCountDao.selectPageByConditions",
				"com.youxue.core.dao.WordCountDao.selectCountByConditions", conditions);
	}

}
