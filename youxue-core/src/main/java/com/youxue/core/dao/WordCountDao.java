package com.youxue.core.dao;

import java.util.Map;

import com.youxue.core.vo.Page;
import com.youxue.core.vo.WordCountVo;

public interface WordCountDao
{
	int deleteByPrimaryKey(String word);

	int insert(WordCountVo record);

	int insertSelective(WordCountVo record);

	WordCountVo selectByPrimaryKey(String word);

	int updateByPrimaryKeySelective(WordCountVo record);

	int updateByPrimaryKey(WordCountVo record);

	Page<WordCountVo> selectPageByConditions(Page<WordCountVo> page, Map<String, Object> conditions);
}