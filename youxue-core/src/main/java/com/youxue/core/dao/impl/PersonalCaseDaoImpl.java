package com.youxue.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.PersonalCaseDao;
import com.youxue.core.vo.Page;
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

	@Override
	public Page<PersonalCaseVo> selectByPage(int pageNo, int i)
	{
		Page<PersonalCaseVo> page = new Page<PersonalCaseVo>(pageNo, i);
		return getPageList(page, "com.youxue.core.dao.PersonalCaseDao.selectByConditions",
				"com.youxue.core.dao.PersonalCaseDao.selectCountByConditions", Maps.newHashMap(), sqlSessionTemplate);
	}

}
