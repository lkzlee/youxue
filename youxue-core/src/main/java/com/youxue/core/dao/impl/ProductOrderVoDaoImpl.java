package com.youxue.core.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.youxue.core.dao.BaseDao;
import com.youxue.core.dao.ProductOrderVoDao;
import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.ProductOrderVo;

@Repository
public class ProductOrderVoDaoImpl extends BaseDao implements ProductOrderVoDao
{

	@Override
	public int deleteByPrimaryKey(String orderId)
	{
		return sqlSessionTemplate.delete("com.youxue.core.dao.ProductOrderVoDao.deleteByPrimaryKey", orderId);
	}

	@Override
	public int insert(ProductOrderVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.ProductOrderVoDao.insert", record);
	}

	@Override
	public int insertSelective(ProductOrderVo record)
	{
		return sqlSessionTemplate.insert("com.youxue.core.dao.ProductOrderVoDao.insertSelective", record);
	}

	@Override
	public ProductOrderVo selectByPrimaryKey(String orderId)
	{
		return sqlSessionTemplate.selectOne("com.youxue.core.dao.ProductOrderVoDao.selectByPrimaryKey", orderId);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductOrderVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.ProductOrderVoDao.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(ProductOrderVo record)
	{
		return sqlSessionTemplate.update("com.youxue.core.dao.ProductOrderVoDao.updateByPrimaryKey", record);
	}

	@Override
	public Page<ProductOrderVo> selectPageOrderListByInfo(Page<ProductOrderVo> page, PayTypeEnum payType, String orderId,
			String accountId, Date startTime, Date endTime)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		if (payType != null && PayTypeEnum.UNKNOW_PAY != payType)
			param.put("payType", payType.getValue());
		if (StringUtils.isNotBlank(orderId))
			param.put("orderId", orderId);
		if (StringUtils.isNotBlank(accountId))
			param.put("accountId", accountId);
		if (startTime != null)
			param.put("startTime", startTime);
		if (endTime != null)
			param.put("endTime", endTime);
		return getPageList(page, "com.youxue.core.dao.ProductOrderVoDao.selectPageOrderListByInfo",
				"com.youxue.core.dao.ProductOrderVoDao.selectCountPageOrderListByInfo", param);
	}

}
