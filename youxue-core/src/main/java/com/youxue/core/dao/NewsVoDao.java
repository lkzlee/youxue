package com.youxue.core.dao;

import com.youxue.core.vo.NewsVo;

public interface NewsVoDao {
    int deleteByPrimaryKey(String newsId);

    int insert(NewsVo record);

    int insertSelective(NewsVo record);

    NewsVo selectByPrimaryKey(String newsId);

    int updateByPrimaryKeySelective(NewsVo record);

    int updateByPrimaryKey(NewsVo record);
}