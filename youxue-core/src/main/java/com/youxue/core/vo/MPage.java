package com.youxue.core.vo;

public class MPage<T> extends Page<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer unReadCount;

	public MPage()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public MPage(int pageNo, int pageSize)
	{
		super(pageNo, pageSize);
		// TODO Auto-generated constructor stub
	}

	public MPage(int pageSize)
	{
		super(pageSize);
		// TODO Auto-generated constructor stub
	}

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
