package com.youxue.core.util;

import java.io.InputStream;

import com.aliyun.oss.OSSClient;

public class AliOSSUtil
{
	public static String uploadFileOSS(InputStream ins, String filePath)
	{
		String endpoint = PropertyUtils.getProperty("endpoint", "https://oss-cn-beijing.aliyuncs.com");
		String accessKeyId = PropertyUtils.getProperty("accessKeyId", "LTAIGX90Jtg8yPuK");
		String accessKeySecret = PropertyUtils.getProperty("accessKeySecret", "TMK357pNL0od8wgVmpQLVef9o6XLg6");
		String userAccessUrl = PropertyUtils.getProperty("userAccessUrl", "http://oss.camplink.cn");
		String bucketName = PropertyUtils.getProperty("bucketName", "camplink-igalaxy");
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject(bucketName, filePath, ins);
		// 关闭client
		ossClient.shutdown();
		return userAccessUrl + "/" + filePath;
	}
}
