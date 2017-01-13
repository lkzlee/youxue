package com.youxue.core.dao;

import com.youxue.core.vo.NewsVo;
import com.youxue.core.vo.Page;

public interface NewsVoDao
{
	int deleteByPrimaryKey(String newsId);

	int insert(NewsVo record);

	int insertSelective(NewsVo record);

	NewsVo selectByPrimaryKey(String newsId);

	int updateByPrimaryKeySelective(NewsVo record);

	int updateByPrimaryKey(NewsVo record);

	Page<NewsVo> selectByPage(int pageNo, int pageSize);
}