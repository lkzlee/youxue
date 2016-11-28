package com.youxue.core.constant;

public class RedisConstant
{
	public static final String MOBILE_LOGIN_IMG_SECCODE = "LOGIN_IMGCODE_";
	public static final String MOBILE_LOGIN_PHONE_SECCODE = "LOGIN_PHONECODE_";
	public static final String EMAIL_VERIFY_KEY = "email_verify_key_";

	public static final String SEARCH_MAP_KEY = "s_map_key";

	/**购物车*/
	public static final String SHOP_CART_KEY = "S_CART_";
	/**购物车*/
	public static final String ADD_USER_ORDER_KEY = "add_user_order_key_";

	public static String getEmailVerifyKey(String accountId)
	{
		return EMAIL_VERIFY_KEY + accountId;
	}

	public static String getAddUserOrderKey(String accountId, String logicOrderId)
	{
		return ADD_USER_ORDER_KEY + accountId + "_" + logicOrderId;
	}
}
