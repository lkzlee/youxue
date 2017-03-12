package com.youxue.core.enums;

import java.text.MessageFormat;

public enum MessageEnum
{
	WAIT_AUDIT("【营联天下】已收到您的订单（订单编号：{0}），请耐心等待审核。", "订单成功提醒"), WAIT_DEPARTURE("【营联天下】您的订单(订单编号：{0}) 已通过审核，请您准备好出行！",
			"订单审核通过"), REFUNDED("【营联天下】您的订单(订单编号：{0}) 已成功申请退款，支付金额将按照原支付方式退还，请注意查收！", "订单退款提醒"), AUDIT_FAILED(
			"【营联天下】您的订单(订单编号：{0}) 未通过审核，支付金额将按照原支付方式退还，请注意查收！", "订单审核未通过");
	private String desc;
	private String title;

	private MessageEnum(String desc, String title)
	{
		this.setDesc(desc);
		this.setTitle(title);

	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static void main(String[] args)
	{
		System.out.println(MessageFormat.format("{0}  今年{1} 岁", "我", 24));
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}
