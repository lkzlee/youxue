package com.youxue.core.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.youxue.core.vo.Page;

@Repository("baseDao")
public class BaseDao
{
	@Resource(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate;

	public <T> Page<T> getPageList(Page<T> page, String listSql, String countSql, Object param)
	{
		return getPageList(page, listSql, countSql, param, sqlSessionTemplate);
	}

	public <T> Page<T> getPageList(Page<T> page, String listSql, String countSql, Object param,
			SqlSessionTemplate sqlSessionTemplate)
	{
		int skipResults = (page.getPageNo() - 1) * page.getPageSize();
		if (page.getPageNo() < 1)
		{
			skipResults = 0;
		}
		int maxResults = page.getPageSize();
		List<T> resultList = sqlSessionTemplate.selectList(listSql, param, new RowBounds(skipResults, maxResults));
		page.setResultList(resultList);
		int totalCount = sqlSessionTemplate.selectOne(countSql, param);
		page.setTotalCount(totalCount);
		page.setResult(100);
		page.setResultDesc("查询成功");
		return page;
	}
}