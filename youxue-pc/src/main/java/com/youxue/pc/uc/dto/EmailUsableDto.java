package com.youxue.pc.uc.dto;

import java.io.Serializable;

import com.youxue.core.common.BaseResponseDto;

public class EmailUsableDto extends BaseResponseDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isUsed;

	public boolean getIsUsed()
	{
		return isUsed;
	}

	public void setIsUsed(boolean isUsed)
	{
		this.isUsed = isUsed;
	}

	@Override
	public String toString()
	{
		return "EmailUsableDto [isUsed=" + isUsed + "]";
	}

}
