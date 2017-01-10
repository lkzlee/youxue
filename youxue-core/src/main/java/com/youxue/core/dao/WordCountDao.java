package com.youxue.core.dao;

import com.youxue.core.vo.WordCountVo;

public interface WordCountDao
{
	int deleteByPrimaryKey(String word);

	int insert(WordCountVo record);

	int insertSelective(WordCountVo record);

	WordCountVo selectByPrimaryKey(String word);

	int updateByPrimaryKeySelective(WordCountVo record);

	int updateByPrimaryKey(WordCountVo record);
}