package com.youxue.pc.uc.dto;

import java.io.Serializable;

import com.youxue.core.common.BaseResponseDto;

public class EmailActiveDto extends BaseResponseDto implements Serializable
{
	private int activeStatus;
	private String activeEmail;
	private String activeEmailUrl;

	public int getActiveStatus()
	{
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus)
	{
		this.activeStatus = activeStatus;
	}

	public String getActiveEmail()
	{
		return activeEmail;
	}

	public void setActiveEmail(String activeEmail)
	{
		this.activeEmail = activeEmail;
	}

	@Override
	public String toString()
	{
		return "EmailActiveDto [activeStatus=" + activeStatus + ", activeEmail=" + activeEmail + "]";
	}

	public String getActiveEmailUrl()
	{
		return activeEmailUrl;
	}

	public void setActiveEmailUrl(String activeEmailUrl)
	{
		this.activeEmailUrl = activeEmailUrl;
	}
}
