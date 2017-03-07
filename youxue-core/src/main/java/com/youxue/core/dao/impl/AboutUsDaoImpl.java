package com.youxue.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.AboutUsDao;
import com.youxue.core.dao.BaseDao;
import com.youxue.core.vo.AboutUsVo;

@Repository
public class AboutUsDaoImpl extends BaseDao implements AboutUsDao
{

	@Override
	public int deleteByPrimaryKey(String type)
	{
		return this.sqlSessionTemplate.delete("com.youxue.core.dao.AboutUsDao.deleteByPrimaryKey", type);
	}

	@Override
	public int insert(AboutUsVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.AboutUsDao.insert", record);
	}

	@Override
	public int insertSelective(AboutUsVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.AboutUsDao.insertSelective", record);
	}

	@Override
	public AboutUsVo selectByPrimaryKey(String type)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.AboutUsDao.selectByPrimaryKey", type);
	}

	@Override
	public int updateByPrimaryKeySelective(AboutUsVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.AboutUsDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(AboutUsVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.AboutUsDao.updateByPrimaryKey", record);
	}

	@Override
	public List<AboutUsVo> selectAll()
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.AboutUsDao.selectAll");
	}

}
