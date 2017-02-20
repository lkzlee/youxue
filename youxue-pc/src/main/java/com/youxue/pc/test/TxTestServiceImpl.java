package com.youxue.pc.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youxue.core.dao.CampsDao;
import com.youxue.core.vo.CampsVo;

@Service
public class TxTestServiceImpl implements TxTestService
{

	Log LOG = LogFactory.getLog(TxTestServiceImpl.class);
	@Autowired
	CampsDao campsDao;
	@Autowired
	Tx1TestService tx1TestService;

	@Transactional
	@Override
	public void test1()
	{
		CampsVo record = new CampsVo();
		record.setCampsId("test" + System.currentTimeMillis());
		campsDao.insert(record);

		try
		{
			tx1TestService.test2();
		}
		catch (Exception e)
		{
			LOG.error("error", e);
		}
	}

	@Transactional
	@Override
	public void test1WithOutTx()
	{
		CampsVo record = new CampsVo();
		record.setCampsId("test1" + System.currentTimeMillis());
		campsDao.insert(record);

		try
		{
			tx1TestService.test2WithoutTx();
		}
		catch (Exception e)
		{
			LOG.error("error", e);
		}
	}
}
