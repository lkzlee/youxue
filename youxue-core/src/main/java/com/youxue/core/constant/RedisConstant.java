package com.youxue.core.constant;

public class RedisConstant
{
	public static final String MOBILE_LOGIN_IMG_SECCODE = "LOGIN_IMGCODE_";
	public static final String MOBILE_LOGIN_PHONE_SECCODE = "LOGIN_PHONECODE_";
	public static final String EMAIL_VERIFY_KEY = "email_verify_key_";

	public static String getEmailVerifyKey(String accountId)
	{
		return EMAIL_VERIFY_KEY + accountId;
	}
}
