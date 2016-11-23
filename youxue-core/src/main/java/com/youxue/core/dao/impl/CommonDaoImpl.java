package com.youxue.core.dao.impl;

import org.springframework.stereotype.Service;

import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.dao.CommonDao;
import com.youxue.core.util.RandomUuidFactory;

@Service
public class CommonDaoImpl implements CommonDao
{

	@Override
	public String getIdByPrefix(String prefix)
	{
		String id = DateUtil.formatDate(DateUtil.getCurrentTimestamp(), DateUtil.DATE_FORMAT_YYYYMMDDHH);
		id = id + prefix + RandomUuidFactory.getInstance().createUUID(12);
		return id;
	}
}
