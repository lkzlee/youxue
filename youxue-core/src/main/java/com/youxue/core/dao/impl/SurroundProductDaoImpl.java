package com.youxue.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.SurroundProductDao;
import com.youxue.core.vo.SurroundProductVo;

@Repository
public class SurroundProductDaoImpl extends BaseDao implements SurroundProductDao
{

	@Override
	public int deleteByPrimaryKey(String productId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.SurroundProductDao.deleteByPrimaryKey", productId);
	}

	@Override
	public int insert(SurroundProductVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.SurroundProductDao.insert", record);
	}

	@Override
	public int insertSelective(SurroundProductVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.SurroundProductDao.insertSelective", record);
	}

	@Override
	public SurroundProductVo selectByPrimaryKey(String productId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.SurroundProductDao.selectByPrimaryKey", productId);

	}

	@Override
	public int updateByPrimaryKeySelective(SurroundProductVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.SurroundProductDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(SurroundProductVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.SurroundProductDao.updateByPrimaryKey", record);
	}

	@Override
	public SurroundProductVo selectProductByType(int type)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.SurroundProductDao.selectProductByType", type);
	}

}