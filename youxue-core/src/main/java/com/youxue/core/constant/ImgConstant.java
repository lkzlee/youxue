package com.youxue.core.constant;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.youxue.core.util.PropertyUtils;

public class ImgConstant
{
	public static final String IMG_BASE_URL = "/";
	public static final String MOBLIE_SECCODE_COOKIE_NAME = "sec_code";

	//	public static String getImgUrl(String imgUrl, String imgType, String imgName)
	//	{
	//		return IMG_BASE_URL + "/" + imgUrl + "/" + imgName + "." + imgType;
	//	}

	/**
	 * @return 获取图片的存储机器上存储路径
	 */
	public static String getImgFilePath(String imgPath)
	{
		String filePath = "D:/img/" + imgPath;
		return filePath;
	}

	/**
	 * @param imgUrl 图片类型,如camps
	 * @param imgFileName 文件名，临时生成如campsId
	 * @return 根据数据库中图片路径获取图片的http路径
	 */
	public static String getHttpImgUrl(String imgType, String imgFileName)
	{
		if (StringUtils.isBlank(imgType) || StringUtils.isBlank(imgFileName))
			return "";
		String httpUrl = PropertyUtils.getProperty("ImgDomain", "http://localhost:8080/img/") + imgType
				+ File.separator + imgFileName;
		return httpUrl;
	}

	public static void main(String[] args)
	{
		System.out.println(PropertyUtils.getProperty("ImgDomain", "http://localhost:8080/img/") + "aaa/test.png");
	}
}
