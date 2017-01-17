package com.youxue.core.dto;

import com.youxue.core.common.BaseResponseDto;

/**
 * @author Masterwind
 * 2017年1月17日上午10:49:40

 * @Description 搜索关键词dto
 */
public class SearchDto extends BaseResponseDto
{

	private static final long serialVersionUID = 1L;
	private String searchKey;
	private String searchCount;
	private String lastDate;

	public String getSearchKey()
	{
		return searchKey;
	}

	public void setSearchKey(String searchKey)
	{
		this.searchKey = searchKey;
	}

	public String getSearchCount()
	{
		return searchCount;
	}

	public void setSearchCount(String searchCount)
	{
		this.searchCount = searchCount;
	}

	public String getLastDate()
	{
		return lastDate;
	}

	public void setLastDate(String lastDate)
	{
		this.lastDate = lastDate;
	}

}
