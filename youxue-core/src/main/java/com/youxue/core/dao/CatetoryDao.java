package com.youxue.core.dao;

import com.youxue.core.vo.CatetoryVo;

public interface CatetoryDao {
    int deleteByPrimaryKey(String categoryId);

    int insert(CatetoryVo record);

    int insertSelective(CatetoryVo record);

    CatetoryVo selectByPrimaryKey(String categoryId);

    int updateByPrimaryKeySelective(CatetoryVo record);

    int updateByPrimaryKey(CatetoryVo record);
}