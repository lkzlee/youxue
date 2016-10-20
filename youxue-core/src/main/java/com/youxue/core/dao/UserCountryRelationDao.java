package com.youxue.core.dao;

import com.youxue.core.vo.UserCountryRelationVo;

public interface UserCountryRelationDao {
    int insert(UserCountryRelationVo record);

    int insertSelective(UserCountryRelationVo record);
}