package com.youxue.admin.constant;

import java.util.HashMap;
import java.util.Map;

import com.youxue.core.enums.PayTypeEnum;

public class ConstantMapUtil
{

	public final static Map<String, String> orderStatusMap = new HashMap<String, String>();

	public final static Map<String, String> payTypeMap = new HashMap<String, String>();

	public final static Map<String, String> productTypeMap = new HashMap<String, String>();
	static
	{
		orderStatusMap.put("0", "待支付");
		orderStatusMap.put("1", "待审核");
		orderStatusMap.put("2", "待出行");
		orderStatusMap.put("3", "已完成");
		orderStatusMap.put("4", "已取消");

		payTypeMap.put("0", "全部");
		payTypeMap.put("" + PayTypeEnum.ALIPAY.getValue(), "支付宝支付");
		payTypeMap.put("" + PayTypeEnum.WEIXIN_APY.getValue(), "微信支付");

		productTypeMap.put("1", "签证服务");
		productTypeMap.put("2", "接送机服务");
	}
}
