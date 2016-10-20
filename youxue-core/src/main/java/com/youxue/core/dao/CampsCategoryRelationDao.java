package com.youxue.core.dao;

import com.youxue.core.vo.CampsCategoryRelationVo;

public interface CampsCategoryRelationDao {
    int insert(CampsCategoryRelationVo record);

    int insertSelective(CampsCategoryRelationVo record);
}