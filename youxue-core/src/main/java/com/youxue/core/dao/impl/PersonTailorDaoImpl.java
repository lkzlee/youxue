package com.youxue.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.PersonTailorDao;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.PersonTailorVo;

@Repository
public class PersonTailorDaoImpl extends BaseDao implements PersonTailorDao
{

	@Override
	public int deleteByPrimaryKey(String id)
	{
		return this.sqlSessionTemplate.delete("com.youxue.core.dao.PersonTailorDao.deleteByPrimaryKey", id);
	}

	@Override
	public int insert(PersonTailorVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.PersonTailorDao.insert", record);
	}

	@Override
	public int insertSelective(PersonTailorVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.PersonTailorDao.insertSelective", record);
	}

	@Override
	public PersonTailorVo selectByPrimaryKey(String id)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.PersonTailorDao.selectByPrimaryKey", id);
	}

	@Override
	public int updateByPrimaryKeySelective(PersonTailorVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.PersonTailorDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(PersonTailorVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.PersonTailorDao.updateByPrimaryKey", record);
	}

	@Override
	public Page<PersonTailorVo> selectByPage(int pageNo, int i)
	{
		Page<PersonTailorVo> page = new Page<PersonTailorVo>(pageNo, i);
		return getPageList(page, "com.youxue.core.dao.PersonTailorDao.selectByConditions",
				"com.youxue.core.dao.PersonTailorDao.selectCountByConditions", Maps.newHashMap(), sqlSessionTemplate);
	}
}
