package com.youxue.core.constant;

public class ImgConstant
{
	public static final String IMG_BASE_URL = "/";
	public static final String MOBLIE_SECCODE_COOKIE_NAME = "sec_code";

	//	public static String getImgUrl(String imgUrl, String imgType, String imgName)
	//	{
	//		return IMG_BASE_URL + "/" + imgUrl + "/" + imgName + "." + imgType;
	//	}

	public static String getImgUrl(String imgUrl, String imgType, String imgName)
	{
		String filePath = "D:\\img\\" + imgUrl + "\\" + imgName + "." + imgType;
		return filePath;
	}
}
