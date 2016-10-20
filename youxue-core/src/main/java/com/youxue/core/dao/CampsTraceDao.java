package com.youxue.core.dao;

import com.youxue.core.vo.CampsTraceVo;

public interface CampsTraceDao {
    int deleteByPrimaryKey(String traceId);

    int insert(CampsTraceVo record);

    int insertSelective(CampsTraceVo record);

    CampsTraceVo selectByPrimaryKey(String traceId);

    int updateByPrimaryKeySelective(CampsTraceVo record);

    int updateByPrimaryKey(CampsTraceVo record);
}