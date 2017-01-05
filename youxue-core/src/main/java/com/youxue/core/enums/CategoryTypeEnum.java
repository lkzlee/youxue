package com.youxue.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Masterwind
 * 2017年1月5日下午7:59:37
 * @Description 分类大类别 ： 1跟2给热门、特价预留
 */
public enum CategoryTypeEnum
{
	LOCALE(3, "目的地国家分类"), SUBJECT(4, "主题分类"), DURATION(5, "时间周期"), DEPARTURETIME(6, "出发时间"), PRICE(7, "价格档位");
	private CategoryTypeEnum(int value, String desc)
	{
		this.setValue(value);
		this.setDesc(desc);
	}

	public static CategoryTypeEnum getByValue(Integer value)
	{
		for (CategoryTypeEnum type : CategoryTypeEnum.values())
		{
			if (type.getValue() == value)
			{
				return type;
			}
		}
		return null;
	}

	private static Map<Integer, String> cateTypeMap = new HashMap<>();
	static
	{
		for (CategoryTypeEnum type : CategoryTypeEnum.values())
		{
			cateTypeMap.put(type.getValue(), type.getDesc());
		}
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

	public static Map<Integer, String> getCateTypeMap()
	{
		return cateTypeMap;
	}

	private int value;
	private String desc;
}
