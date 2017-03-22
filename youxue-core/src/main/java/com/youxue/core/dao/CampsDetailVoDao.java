package com.youxue.core.dao;

import com.youxue.core.vo.CampsDetailVo;

public interface CampsDetailVoDao {
    int deleteByPrimaryKey(String detailId);

    int insert(CampsDetailVo record);

    int insertSelective(CampsDetailVo record);

    CampsDetailVo selectByPrimaryKey(String detailId);

    int updateByPrimaryKeySelective(CampsDetailVo record);

    int updateByPrimaryKey(CampsDetailVo record);
}