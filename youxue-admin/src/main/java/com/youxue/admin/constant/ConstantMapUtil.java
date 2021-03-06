package com.youxue.admin.constant;

import java.util.HashMap;
import java.util.Map;

import com.youxue.core.enums.PayTypeEnum;
import com.youxue.core.vo.OrderVo;

public class ConstantMapUtil
{

	public final static Map<String, String> orderStatusMap = new HashMap<String, String>();

	public final static Map<String, String> payTypeMap = new HashMap<String, String>();

	public final static Map<String, String> productTypeMap = new HashMap<String, String>();
	static
	{
		orderStatusMap.put("" + OrderVo.UNPAY, "待支付");
		orderStatusMap.put("" + OrderVo.PAY, "待审核");
		orderStatusMap.put("" + OrderVo.TO_OUT, "待出行");
		orderStatusMap.put("" + OrderVo.DONE, "已完成");
		orderStatusMap.put("" + OrderVo.APPLY_REFUND, "申请退款");
		orderStatusMap.put("" + OrderVo.APPLY_FAILED, "退款失败");
		orderStatusMap.put("" + OrderVo.CANCEL, "已取消,已退款");
		orderStatusMap.put("" + OrderVo.DELETED, "已删除，已取消（未支付订单）");
		payTypeMap.put("0", "全部");
		payTypeMap.put("" + PayTypeEnum.ALIPAY.getValue(), "支付宝支付");
		payTypeMap.put("" + PayTypeEnum.WEIXIN_PAY.getValue(), "微信支付");
		payTypeMap.put("" + PayTypeEnum.WEIXIN_JS_API.getValue(), "微信JSAPI支付");

		productTypeMap.put("1", "签证服务");
		productTypeMap.put("2", "接送机服务");
	}
}
