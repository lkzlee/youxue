package com.youxue.core.mybatis.dialect;

public abstract class Dialect
{
	public abstract String getLimitString(String sql, int skipResults, int maxResults);

}
