package com.youxue.core.dao;

import java.util.Date;

import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.vo.Page;
import com.youxue.core.vo.ProductOrderVo;

public interface ProductOrderVoDao
{
	int deleteByPrimaryKey(String orderId);

	int insert(ProductOrderVo record);

	int insertSelective(ProductOrderVo record);

	ProductOrderVo selectByPrimaryKey(String orderId);

	int updateByPrimaryKeySelective(ProductOrderVo record);

	int updateByPrimaryKey(ProductOrderVo record);

	Page<ProductOrderVo> selectPageOrderListByInfo(Page<ProductOrderVo> page, PayTypeEnum pType, String orderId,
			String accountId, Date stTime, Date edTime);
}