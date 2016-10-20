package com.youxue.core.dao;

import com.youxue.core.vo.CampsVo;

public interface CampsDao {
    int deleteByPrimaryKey(String campsId);

    int insert(CampsVo record);

    int insertSelective(CampsVo record);

    CampsVo selectByPrimaryKey(String campsId);

    int updateByPrimaryKeySelective(CampsVo record);

    int updateByPrimaryKey(CampsVo record);
}