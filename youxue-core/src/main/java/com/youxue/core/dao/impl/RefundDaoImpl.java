package com.youxue.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.RefundDao;
import com.youxue.core.vo.RefundVo;

@Repository
public class RefundDaoImpl extends BaseDao implements RefundDao
{

	@Override
	public int deleteByPrimaryKey(String orderId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.RefundDao.deleteByPrimaryKey", orderId);
	}

	@Override
	public int insert(RefundVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.RefundDao.insert", record);
	}

	@Override
	public int insertSelective(RefundVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.RefundDao.insertSelective", record);
	}

	@Override
	public RefundVo selectByPrimaryKey(String orderId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.RefundDao.selectByPrimaryKey", orderId);
	}

	@Override
	public int updateByPrimaryKeySelective(RefundVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.RefundDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(RefundVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.RefundDao.updateByPrimaryKey", record);
	}

}
