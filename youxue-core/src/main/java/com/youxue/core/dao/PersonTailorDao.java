package com.youxue.core.dao;

import com.youxue.core.vo.Page;
import com.youxue.core.vo.PersonTailorVo;

public interface PersonTailorDao
{
	int deleteByPrimaryKey(String id);

	int insert(PersonTailorVo record);

	int insertSelective(PersonTailorVo record);

	PersonTailorVo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(PersonTailorVo record);

	int updateByPrimaryKey(PersonTailorVo record);

	Page<PersonTailorVo> selectByPage(int pageNo, int i);
}