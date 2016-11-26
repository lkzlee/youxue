package com.youxue.pc.uc.dto;

import com.youxue.core.vo.Page;

public class MessagePage extends Page
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer unReadCount;

	public Integer getUnReadCount()
	{
		return unReadCount;
	}

	public void setUnReadCount(Integer unReadCount)
	{
		this.unReadCount = unReadCount;
	}

	@Override
	public String toString()
	{
		return "MessagePage [unReadCount=" + unReadCount + "]";
	}

}
