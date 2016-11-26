package com.youxue.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CouponCodeVo
{
	public static final int NORMAL = 1;

	private String codeId;

	private String codeValue;

	private BigDecimal codeAmount;

	private Date createTime;

	private Date startTime;

	private Date endTime;

	private Integer status;

	private Integer useCount;

	private String categoryIds;

	public String getCodeId()
	{
		return codeId;
	}

	public void setCodeId(String codeId)
	{
		this.codeId = codeId == null ? null : codeId.trim();
	}

	public String getCodeValue()
	{
		return codeValue;
	}

	public void setCodeValue(String codeValue)
	{
		this.codeValue = codeValue == null ? null : codeValue.trim();
	}

	public BigDecimal getCodeAmount()
	{
		return codeAmount;
	}

	public void setCodeAmount(BigDecimal codeAmount)
	{
		this.codeAmount = codeAmount;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getUseCount()
	{
		return useCount;
	}

	public void setUseCount(Integer useCount)
	{
		this.useCount = useCount;
	}

	public String getCategoryIds()
	{
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds)
	{
		this.categoryIds = categoryIds == null ? null : categoryIds.trim();
	}
}