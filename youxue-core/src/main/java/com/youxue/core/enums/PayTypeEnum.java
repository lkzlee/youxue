package com.youxue.core.enums;

public enum PayTypeEnum
{
	UNKNOW_PAY(0, "未定义"), ALIPAY(1, "支付宝支付"), WEIXIN_APY(2, "微信支付");

	private int value;
	private String desc;

	private PayTypeEnum(int value, String desc)
	{
		this.setValue(value);
		this.setDesc(desc);
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static PayTypeEnum getByValue(Integer payType)
	{
		if (payType == null)
			return PayTypeEnum.UNKNOW_PAY;
		for (PayTypeEnum py : PayTypeEnum.values())
		{
			if (payType == py.getValue())
				return py;
		}
		return null;
	}

}
