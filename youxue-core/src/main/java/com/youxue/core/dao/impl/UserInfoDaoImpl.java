package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.vo.Page;
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

	@Override
	public UserInfoVo selectByEmail(String email)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.UserInfoDao.selectByEmail", email);
	}

	@Override
	public Page<UserInfoVo> selectPageUserInfoListByInfo(Page<UserInfoVo> page, String accountId, String nickName)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(accountId))
			param.put("accountId", accountId);
		if (StringUtils.isNotBlank(nickName))
			param.put("nickName", nickName);
		return getPageList(page, "com.youxue.core.dao.UserInfoDao.selectPageUserInfoListByInfo",
				"com.youxue.core.dao.UserInfoDao.selectCountPageUserInfoListByInfo", param);
	}
}
