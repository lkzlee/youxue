package com.youxue.core.vo;

import java.util.Date;

public class WordCountVo
{
	private String word;

	private Long count;

	private Date lastSearchTime;

	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word == null ? null : word.trim();
	}

	public Long getCount()
	{
		return count;
	}

	public void setCount(Long count)
	{
		this.count = count;
	}

	public Date getLastSearchTime()
	{
		return lastSearchTime;
	}

	public void setLastSearchTime(Date lastSearchTime)
	{
		this.lastSearchTime = lastSearchTime;
	}
}