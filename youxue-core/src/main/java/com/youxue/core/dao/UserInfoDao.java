package com.youxue.core.dao;

import com.youxue.core.vo.Page;
import com.youxue.core.vo.UserInfoVo;

public interface UserInfoDao
{
	int deleteByPrimaryKey(String accountId);

	int insert(UserInfoVo record);

	int insertSelective(UserInfoVo record);

	UserInfoVo selectByPrimaryKey(String accountId);

	int updateByPrimaryKeySelective(UserInfoVo record);

	int updateByPrimaryKey(UserInfoVo record);

	UserInfoVo selectByEmail(String email);

	Page<UserInfoVo> selectPageUserInfoListByInfo(Page<UserInfoVo> page, String accountId, String nickName);

	UserInfoVo selectUserInfoByOpenId(String openId);
}