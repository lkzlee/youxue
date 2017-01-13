package com.youxue.core.vo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/***
 * 
 * @author lkzlee
 *
 * @param <T>
 *
 * 2016年11月7日
 */
public class Page<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2023727607665537363L;

	public static final int DEFAULT_PAGESIZE = 10;

	public static final int SIX_PAGESIZE = 6;
	protected int result;
	protected String resultDesc;
	protected List<T> resultList = Collections.emptyList();
	protected long totalCount = -1;
	protected int pageNo = 1;
	protected int totalPage = 1;
	protected int pageSize;

	public Page()
	{
		super();
		this.pageSize = DEFAULT_PAGESIZE;
	}

	public Page(int pageNo, int pageSize)
	{
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public Page(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getResult()
	{
		return result;
	}

	public void setResult(int result)
	{
		this.result = result;
	}

	public String getResultDesc()
	{
		return resultDesc;
	}

	public void setResultDesc(String resultDesc)
	{
		this.resultDesc = resultDesc;
	}

	public List<T> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<T> resultList)
	{
		this.resultList = resultList;
	}

	public long getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
		if (totalCount == 0)
		{
			this.totalPage = 0;
		}
		else
		{
			this.totalPage = totalCount % this.getPageSize() == 0 ? totalCount / this.getPageSize() : totalCount
					/ this.getPageSize() + 1;
		}
	}

	public int getPageNo()
	{
		if (pageNo <= 0)
		{
			pageNo = 1;
		}
		return pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo;
	}

	public int getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}

	public void pageNoPlus(int i)
	{
		this.pageNo += i;
	}

	public static <T> Page<T> getInstantce(int pageNo, int pageSize, Class<T> c)
	{
		Page<T> page = new Page<>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return page;
	}

	public static int getPageNo(String pageNo)
	{
		int pNum = 1;
		if (StringUtils.isBlank(pageNo))
			return pNum;
		try
		{
			pNum = Integer.parseInt(pageNo);
		}
		catch (Exception e)
		{
			pNum = 1;
		}
		return pNum;
	}

}
