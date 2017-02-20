package com.youxue.pc.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Tx1TestServiceImpl implements Tx1TestService
{
	@Override
	@Transactional
	public void test2()
	{
		throw new NullPointerException("null point exp test2");
	}

	@Override
	public void test2WithoutTx()
	{
		throw new NullPointerException("null point exp test2WithoutTx");
	}
}
