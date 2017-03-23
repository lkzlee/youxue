package com.youxue.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CampsDetailVo
{
	private String detailId;

	private String detailName;

	private BigDecimal detailPrice;

	private Date detailStartTime;

	private String campsId;

	private Integer duration;

	public String getDetailId()
	{
		return detailId;
	}

	public void setDetailId(String detailId)
	{
		this.detailId = detailId == null ? null : detailId.trim();
	}

	public String getDetailName()
	{
		return detailName;
	}

	public void setDetailName(String detailName)
	{
		this.detailName = detailName == null ? null : detailName.trim();
	}

	public BigDecimal getDetailPrice()
	{
		return detailPrice;
	}

	public void setDetailPrice(BigDecimal detailPrice)
	{
		this.detailPrice = detailPrice;
	}

	public Date getDetailStartTime()
	{
		return detailStartTime;
	}

	public void setDetailStartTime(Date detailStartTime)
	{
		this.detailStartTime = detailStartTime;
	}

	public String getCampsId()
	{
		return campsId;
	}

	public void setCampsId(String campsId)
	{
		this.campsId = campsId == null ? null : campsId.trim();
	}

	public Integer getDuration()
	{
		return duration;
	}

	public void setDuration(Integer duration)
	{
		this.duration = duration;
	}
}