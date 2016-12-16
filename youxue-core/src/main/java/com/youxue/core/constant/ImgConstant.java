package com.youxue.core.constant;

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
		String filePath = PropertyUtils.getProperty("img.root.dir", "/home/qinggu/img/") + imgPath;
		return filePath;
	}

	public static String getHttpImgUrls(String imgPaths)
	{
		if (StringUtils.isBlank(imgPaths))
			return "";
		String[] imgs = imgPaths.split(",");
		String httpPaths = "";
		for (String img : imgs)
		{
			httpPaths = httpPaths + getHttpImgUrl(img) + ",";
		}
		httpPaths = httpPaths.substring(0, httpPaths.length() - 1);
		return httpPaths;
	}

	/**
	 * @return 根据数据库中图片路径获取图片的http路径
	 */
	private static String getHttpImgUrl(String imgPath)
	{
		if (StringUtils.isBlank(imgPath))
			return "";
		//		String httpUrl = "http://101.200.148.203:8000/youxue-img-0.0.1-SNAPSHOT/img/" + imgPath;
		String httpUrl = PropertyUtils.getProperty("ImgDomain",
				"http://101.200.148.203:8000/youxue-img-0.0.1-SNAPSHOT/img/") + imgPath;
		return httpUrl;
	}

	public static void main(String[] args)
	{
		System.out.println(getHttpImgUrls("png/2,jpg/3"));
	}
}
