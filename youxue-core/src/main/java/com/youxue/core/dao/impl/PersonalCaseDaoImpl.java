package com.youxue.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.PersonalCaseDao;
import com.youxue.core.vo.PersonalCaseVo;

@Repository
public class PersonalCaseDaoImpl extends BaseDao implements PersonalCaseDao
{

	@Override
	public int deleteByPrimaryKey(String caseId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.PersonalCaseDao.deleteByPrimaryKey", caseId);
	}

	@Override
	public int insert(PersonalCaseVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.PersonalCaseDao.insert", record);
	}

	@Override
	public int insertSelective(PersonalCaseVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.PersonalCaseDao.insertSelective", record);
	}

	@Override
	public PersonalCaseVo selectByPrimaryKey(String caseId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.PersonalCaseDao.selectByPrimaryKey", caseId);
	}

	@Override
	public int updateByPrimaryKeySelective(PersonalCaseVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.PersonalCaseDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(PersonalCaseVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.PersonalCaseDao.updateByPrimaryKey", record);
	}

}
