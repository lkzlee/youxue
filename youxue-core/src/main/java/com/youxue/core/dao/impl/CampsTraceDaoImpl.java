package com.youxue.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.CampsTraceDao;
import com.youxue.core.vo.CampsTraceVo;

@Repository
public class CampsTraceDaoImpl extends BaseDao implements CampsTraceDao
{

	@Override
	public int deleteByPrimaryKey(String traceId)
	{
		return this.sqlSessionTemplate.delete("com.youxue.core.dao.CampsTraceDao.deleteByPrimaryKey", traceId);
	}

	@Override
	public int insert(CampsTraceVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.CampsTraceDao.insert", record);
	}

	@Override
	public int insertSelective(CampsTraceVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.CampsTraceDao.insertSelective", record);
	}

	@Override
	public CampsTraceVo selectByPrimaryKey(String traceId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.CampsTraceDao.selectByPrimaryKey", traceId);
	}

	@Override
	public int updateByPrimaryKeySelective(CampsTraceVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.CampsTraceDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(CampsTraceVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.CampsTraceDao.updateByPrimaryKey", record);
	}

	@Override
	public List<CampsTraceVo> selectByCampsId(String campusId)
	{
		return sqlSessionTemplate.selectList("com.youxue.core.dao.CampsTraceDao.selectByCampsId", campusId);
	}

}
