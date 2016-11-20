package com.youxue.core.enums;

public enum CategoryTypeEnum
{
	HOT(1, "热门分类"), PRICE(2, "特价分类"), LOCALE(3, "目的地国家分类"), TODO(4, "活动类型分类"), SUBJECT(5, "主题分类");
	private CategoryTypeEnum(int value, String desc)
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

	private int value;
	private String desc;
}
