package com.youxue.core.vo;

import java.util.Date;

import com.youxue.core.constant.ImgConstant;

public class OrderDetailVo extends OrderVo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String campsName;//营地名称

	private String campsImages;

	private String realCampsImages;

	private String campsTitle;

	private Date deadlineDate;

	private Date departureDate;

	private Date startDate;

	private Integer durationTime;//持续天数

	private Integer payStatus;

	public String getCampsName()
	{
		return campsName;
	}

	public void setCampsName(String campsName)
	{
		this.campsName = campsName;
	}

	public String getCampsImages()
	{
		return campsImages;
	}

	public void setCampsImages(String campsImages)
	{
		this.campsImages = campsImages;
	}

	public String getRealCampsImages()
	{
		return ImgConstant.getHttpImgUrls(campsImages);
	}

	public void setRealCampsImages(String realCampsImages)
	{
		this.realCampsImages = realCampsImages;
	}

	public String getCampsTitle()
	{
		return campsTitle;
	}

	public void setCampsTitle(String campsTitle)
	{
		this.campsTitle = campsTitle;
	}

	public Date getDeadlineDate()
	{
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate)
	{
		this.deadlineDate = deadlineDate;
	}

	public Date getDepartureDate()
	{
		return departureDate;
	}

	public void setDepartureDate(Date departureDate)
	{
		this.departureDate = departureDate;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Integer getDurationTime()
	{
		return durationTime;
	}

	public void setDurationTime(Integer durationTime)
	{
		this.durationTime = durationTime;
	}

	public Integer getPayStatus()
	{
		return payStatus;
	}

	public void setPayStatus(Integer payStatus)
	{
		this.payStatus = payStatus;
	}

	@Override
	public String toString()
	{
		return "OrderDetailVo [campsName=" + campsName + ", campsImages=" + campsImages + ", realCampsImages="
				+ realCampsImages + ", campsTitle=" + campsTitle + ", deadlineDate=" + deadlineDate
				+ ", departureDate=" + departureDate + ", startDate=" + startDate + ", durationTime=" + durationTime
				+ ", payStatus=" + payStatus + "]";
	}
}