package com.youxue.core.dao;

import com.youxue.core.vo.UserInfoVo;

public interface UserInfoDao {
    int deleteByPrimaryKey(String accountId);

    int insert(UserInfoVo record);

    int insertSelective(UserInfoVo record);

    UserInfoVo selectByPrimaryKey(String accountId);

    int updateByPrimaryKeySelective(UserInfoVo record);

    int updateByPrimaryKey(UserInfoVo record);
}