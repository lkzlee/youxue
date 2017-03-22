package com.youxue.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.CampsDetailDao;
import com.youxue.core.vo.CampsDetailVo;

@Repository
public class CampsDetailDaoImpl extends BaseDao implements CampsDetailDao
{

	@Override
	public int deleteByPrimaryKey(String detailId)
	{
		return this.sqlSessionTemplate.delete("com.youxue.core.dao.CampsDetailDao.deleteByPrimaryKey", detailId);
	}

	@Override
	public int insert(CampsDetailVo record)
	{
		return this.sqlSessionTemplate.insert("com.youxue.core.dao.CampsDetailDao.insert", record);
	}

	@Override
	public int insertSelective(CampsDetailVo record)
	{
		return this.sqlSessionTemplate.insert("com.youxue.core.dao.CampsDetailDao.insertSelective", record);
	}

	@Override
	public CampsDetailVo selectByPrimaryKey(String detailId)
	{
		return this.sqlSessionTemplate.selectOne("com.youxue.core.dao.CampsDetailDao.selectByPrimaryKey", detailId);
	}

	@Override
	public int updateByPrimaryKeySelective(CampsDetailVo record)
	{
		return this.sqlSessionTemplate.update("com.youxue.core.dao.CampsDetailDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(CampsDetailVo record)
	{
		return this.sqlSessionTemplate.update("com.youxue.core.dao.CampsDetailDao.updateByPrimaryKey", record);
	}

	@Override
	public List<CampsDetailVo> selectByCampsId(String campsId)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsDetailDao.selectByCampsId", campsId);
	}

}
