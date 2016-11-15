package com.youxue.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.vo.UserInfoVo;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDao implements UserInfoDao
{

	@Override
	public int deleteByPrimaryKey(String accountId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.UserInfoDao.deleteByPrimaryKey", accountId);
	}

	@Override
	public int insert(UserInfoVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.UserInfoDao.insert", record);
	}

	@Override
	public int insertSelective(UserInfoVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.UserInfoDao.insertSelective", record);
	}

	@Override
	public UserInfoVo selectByPrimaryKey(String accountId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.UserInfoDao.selectByPrimaryKey", accountId);
	}

	@Override
	public int updateByPrimaryKeySelective(UserInfoVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.UserInfoDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(UserInfoVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.UserInfoDao.updateByPrimaryKey", record);
	}

}
