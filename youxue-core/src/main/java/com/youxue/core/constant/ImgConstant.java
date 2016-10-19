package com.youxue.core.constant;

public class ImgConstant
{
	public static final String IMG_BASE_URL = "/";

	public static String getImgUrl(String imgType, String imgName)
	{
		return IMG_BASE_URL + "/" + imgType + "/" + imgName;
	}
}
