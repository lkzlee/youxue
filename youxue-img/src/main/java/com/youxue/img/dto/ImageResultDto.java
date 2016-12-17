package com.youxue.img.dto;

import com.youxue.core.common.BaseResponseDto;

public class ImageResultDto extends BaseResponseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imageUlr;

	public String getImageUlr()
	{
		return imageUlr;
	}

	public void setImageUlr(String imageUlr)
	{
		this.imageUlr = imageUlr;
	}

	@Override
	public String toString()
	{
		return "ImageResultDto [imageUlr=" + imageUlr + "]";
	}
}
