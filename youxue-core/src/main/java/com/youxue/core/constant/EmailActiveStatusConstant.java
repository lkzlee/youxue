package com.youxue.core.constant;

import java.util.HashMap;
import java.util.Map;

public class EmailActiveStatusConstant
{
	/***
	 * 初始状态
	 */
	public static final Integer INIT = 0;
	/***
	 * 已发邮件
	 */
	public static final Integer SENDED_MAIL = 1;
	/***
	 * 已激活
	 */
	public static final Integer ACTIVED = 2;

	public static final Map<String, String> emailUrl = new HashMap<String, String>();
	static
	{
		emailUrl.put("126.com", "http://mail.126.com");
		emailUrl.put("163.com", "http://mail.163.com");
		emailUrl.put("qq.com", "http://mail.qq.com");
		emailUrl.put("gmail.com", "http://mail.google.com");
		emailUrl.put("sina.com.cn", "http://mail.sina.com.cn/");
		emailUrl.put("sohu.com", "http://mail.sohu.com/");
	}
}
