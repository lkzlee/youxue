package com.youxue.core.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.youxue.core.util.DateUtil;

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

	private String detailStartTimeStr;

	private Integer durationTime;//持续天数

	private Integer payStatus;

	private Integer payType;

	private String codeName; //优惠券标题描述

	private Integer isRefund; //null 表示无退款 ，1表示 已退款，0表示未退款

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
		return campsImages;
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

	public Integer getPayType()
	{
		return payType;
	}

	public void setPayType(Integer payType)
	{
		this.payType = payType;
	}

	public String getCodeName()
	{
		return codeName;
	}

	public void setCodeName(String codeName)
	{
		this.codeName = codeName;
	}

	public Integer getIsRefund()
	{
		return isRefund;
	}

	public void setIsRefund(Integer isRefund)
	{
		this.isRefund = isRefund;
	}

	public String getDetailStartTimeStr()
	{
		if (StringUtils.isNotBlank(detailStartTimeStr))
			return detailStartTimeStr;
		else
			return DateUtil.formatDate(startDate, "yyyy-MM-dd");
	}

	public void setDetailStartTimeStr(String detailStartTimeStr)
	{
		this.detailStartTimeStr = detailStartTimeStr;
	}

	@Override
	public String toString()
	{
		return "OrderDetailVo [campsName=" + campsName + ", campsImages=" + campsImages + ", realCampsImages="
				+ realCampsImages + ", campsTitle=" + campsTitle + ", deadlineDate=" + deadlineDate
				+ ", departureDate=" + departureDate + ", startDate=" + startDate + ", durationTime=" + durationTime
				+ ", payStatus=" + payStatus + ", payType=" + payType + ", codeName=" + codeName + ", isRefund="
				+ isRefund + "]";
	}
}
