package com.youxue.core.constant;

import org.apache.commons.lang.StringUtils;

import com.youxue.core.util.PropertyUtils;

public class ImgConstant
{
	public static final String IMG_BASE_URL = "/";
	public static final String MOBLIE_SECCODE_COOKIE_NAME = "sec_code";

	public static final String CAMPS_IMG_TYPE = "camps";
	public static final String USER_IMG_TYPE = "user";

	//	public static String getImgUrl(String imgUrl, String imgType, String imgName)
	//	{
	//		return IMG_BASE_URL + "/" + imgUrl + "/" + imgName + "." + imgType;
	//	}

	/**
	 * @return 获取图片的存储机器上存储路径
	 */
	public static String getImgFilePath(String imgPath)
	{
		String filePath = PropertyUtils.getProperty("img.root.dir", "/home/qinggu/img/") + imgPath;
		return filePath;
	}

	/**
	 * @return 根据数据库中图片路径获取图片的http路径
	 */
	public static String getHttpImgUrl(String imgPath)
	{
		if (StringUtils.isBlank(imgPath))
			return "";
		//		String httpUrl = "http://101.200.148.203:8000/youxue-img-0.0.1-SNAPSHOT/img/" + imgPath;
		String httpUrl = PropertyUtils
				.getProperty("ImgDomain", "http://101.200.148.203:8000/youxue-img-0.0.1-SNAPSHOT") + imgPath;
		return httpUrl;
	}

	public static void main(String[] args)
	{
	}
}
