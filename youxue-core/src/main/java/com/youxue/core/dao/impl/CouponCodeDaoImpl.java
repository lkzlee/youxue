package com.youxue.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.CouponCodeDao;
import com.youxue.core.vo.CouponCodeVo;

@Repository
public class CouponCodeDaoImpl extends BaseDao implements CouponCodeDao
{

	@Override
	public int deleteByPrimaryKey(String codeId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.CouponCodeDao.deleteByPrimaryKey", codeId);
	}

	@Override
	public int insert(CouponCodeVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.CouponCodeDao.insert", record);
	}

	@Override
	public int insertSelective(CouponCodeVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.CouponCodeDao.insertSelective", record);
	}

	@Override
	public CouponCodeVo selectByPrimaryKey(String codeId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.CouponCodeDao.selectByPrimaryKey", codeId);
	}

	@Override
	public int updateByPrimaryKeySelective(CouponCodeVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.CouponCodeDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(CouponCodeVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.CouponCodeDao.updateByPrimaryKey", record);
	}

	@Override
	public CouponCodeVo selectCouponByCode(String codeValue, boolean lock)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lock", lock);
		param.put("codeValue", codeValue);
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.CouponCodeDao.selectCouponByCode", param);
	}

}
